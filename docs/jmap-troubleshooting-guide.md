# jmap 内存排查实操手册

本手册基于 `io.weli.concurrent.JmapHeapDemo`，**已在本机（JDK 21）实际跑通**。

---

## 一、jmap 能干什么

| 命令 | 用途 | 典型场景 |
|------|------|----------|
| `jmap -histo <pid>` | 堆内对象直方图（类 → 实例数 → 字节） | 看谁占内存最多 |
| `jmap -histo:live <pid>` | 先 Full GC，再统计存活对象 | 排除已可回收的垃圾 |
| `jmap -dump:...` | 导出 `.hprof` 堆转储 | MAT / VisualVM 深度分析 |
| `jmap -heap <pid>` | 堆配置摘要 | **JDK 21+ 常不可用**，见下文替代 |

---

## 二、Demo 在模拟什么

`JmapHeapDemo` 后台线程每 500ms：

- 向静态 `List<byte[]>` 追加 **512 KB** 数组（只增不减 → 模拟泄漏）
- 向静态 `List<String>` 追加 **1000 个** String

因此 jmap 里应看到 **`[B`（byte 数组）和 `java.lang.String` 实例数持续上升**。

---

## 三、启动 Demo

```bash
cd /path/to/java-snippets
mvn compile -DskipTests

# 限制堆 256MB，泄漏更明显（可选）
java -Xmx256m -cp target/classes io.weli.concurrent.JmapHeapDemo
```

输出示例：

```
JmapHeapDemo started, PID = 24665
Run: jmap -histo 24665
...
leak round 1, buffers=1, strings=1000
leak round 2, buffers=2, strings=2000
```

**另开终端**操作，Demo 进程保持运行。

---

## 四、找 PID

```bash
jps -l | grep JmapHeapDemo
# 24665 io.weli.concurrent.JmapHeapDemo
```

---

## 五、对象直方图：`jmap -histo`（最常用）

```bash
jmap -histo 24665 | head -30
```

### 实际输出片段（跑了几轮泄漏后）

```
 num     #instances         #bytes  class name (module)
-------------------------------------------------------
   1:         25002        8459424  [B (java.base@21)
   2:         24093         578232  java.lang.String (java.base@21)
   ...
```

### 怎么读

| 列 | 含义 |
|----|------|
| `num` | 按占用字节排序的名次 |
| `#instances` | 该类的实例个数 |
| `#bytes` | 这些实例合计字节（不含子对象引用链的完整深度，但足够定位大类） |
| `class name` | 类名；`[B`=byte[]，`[C`=char[]，`[Ljava.lang.String;`=String[] |

### 本 Demo 的结论

- **`[B` 排第一、字节数最大** → 大量 byte 数组，对应 `LEAKED_BUFFERS`
- **`java.lang.String` 实例数 ≈ round × 1000** → 对应 `STRING_LEAK`
- 在真实故障里：若某业务 DTO、缓存 Map、char[] 长期霸榜 → 重点查对应代码路径

### 只看存活对象：`jmap -histo:live`

```bash
jmap -histo:live 24665 | head -20
```

会先触发 **Full GC**，再统计。适合区分「真泄漏」和「只是还没 GC 的临时对象」。

实际跑完 GC 后（约 95k 个 String、48MB+ byte[]）：

```
   1:         95410       48928488  [B (java.base@21)
   2:         95240        2285760  java.lang.String (java.base@21)
```

若 `:live` 后某类仍然巨大 → 强引用泄漏嫌疑大。

---

## 六、堆概况：JDK 21 用 `jcmd` 替代 `jmap -heap`

```bash
jmap -heap 24665
# Error: -heap option used
# Cannot connect to core dump or remote debug server. Use jhsdb jmap instead
```

**JDK 21 上 `-heap` 常直接报错**，改用：

```bash
jcmd 24665 GC.heap_info
jcmd 24665 VM.flags
```

`GC.heap_info` 实际输出：

```
 garbage-first heap   total 262144K, used 97332K [0x..., 0x...)
  region size 1024K, 1 young (1024K), 0 survivors (0K)
 Metaspace       used 659K, committed 832K, reserved 1114112K
```

读法：

- `total 262144K` → 堆上限 256MB（与 `-Xmx256m` 一致）
- `used 97332K` → 当前已用
- `G1` region 信息 → 确认 GC 算法

`VM.flags` 可核对 `-Xmx`、`-Xms`、`-XX:+UseG1GC` 等。

---

## 七、堆转储：`jmap -dump`（离线分析）

```bash
jmap -dump:live,format=b,file=/tmp/jmap-heap-demo.hprof 24665
ls -lh /tmp/jmap-heap-demo.hprof
```

实际结果：

```
Heap dump file created [121114628 bytes in 0.219 secs]
-rw-------  116M  /tmp/jmap-heap-demo.hprof
```

| 参数 | 说明 |
|------|------|
| `live` | 只 dump 存活对象（先 GC） |
| `format=b` | 二进制 hprof |
| `file=...` | 输出路径 |

**注意**：dump 会 **Stop-The-World**，生产环境大堆慎用；可先在低峰做，或用 `jcmd <pid> GC.heap_dump /path/file.hprof`（效果类似）。

### 用 MAT / VisualVM 打开

- [Eclipse MAT](https://www.eclipse.org/mat/)：Leak Suspects、Dominator Tree
- JDK 自带 `jvisualvm`：加载 hprof → 类视图 / 实例引用

在 MAT 里搜 `JmapHeapDemo` → 看 `LEAKED_BUFFERS`、`STRING_LEAK` 的 GC Root 路径。

---

## 八、对比：多次 histo 看增长

```bash
PID=$(jps -l | grep JmapHeapDemo | awk '{print $1}')
for i in 1 2 3; do
  echo "===== $i $(date +%T) ====="
  jmap -histo "$PID" | grep -E '^\s+[0-9]+:.*\[B|java.lang.String'
  sleep 3
done
```

若 `[B` 的 `#instances` 每 3 秒 +1、`#bytes` 每 3 秒 +512KB 量级 → 与 Demo 泄漏速率一致，说明抓对了进程。

---

## 九、jstack vs jmap 怎么选

| 症状 | 工具 |
|------|------|
| CPU 高、线程卡住、死锁 | `jstack` |
| OOM、堆占用高、GC 频繁、怀疑泄漏 | `jmap -histo` / heap dump |
| 两者都要 | 先 `jmap -histo` 看堆，再 `jstack` 看是否某线程在疯狂分配 |

---

## 十、常见问题

| 问题 | 处理 |
|------|------|
| `Could not attach to process` | 同用户运行；macOS 可试 `sudo jmap` |
| histo 全是框架类 | 等 Demo 多跑几轮，或 `grep '\[B\|String\|JmapHeapDemo'` |
| dump 文件巨大 | 加 `:live`；或缩小 `-Xmx` 做实验 |
| `-heap` 报错 | 改用 `jcmd <pid> GC.heap_info` |

---

## 十一、清理

```bash
jps -l | grep JmapHeapDemo | awk '{print $1}' | xargs kill
rm -f /tmp/jmap-heap-demo.hprof
```

---

## 十二、一条龙命令

```bash
cd /path/to/java-snippets
mvn -q compile -DskipTests
java -Xmx256m -cp target/classes io.weli.concurrent.JmapHeapDemo &
sleep 5
PID=$(jps -l | grep JmapHeapDemo | awk '{print $1}')
echo "PID=$PID"
jmap -histo "$PID" | head -15
jcmd "$PID" GC.heap_info
jmap -dump:live,format=b,file=/tmp/jmap-heap-demo.hprof "$PID"
kill "$PID"
```

---

## 十三、相关文件

- 示例代码：`src/main/java/io/weli/concurrent/JmapHeapDemo.java`
- 线程排查（姊妹篇）：`docs/jstack-troubleshooting-guide.md`
