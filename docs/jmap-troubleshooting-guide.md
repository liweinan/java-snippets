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

- 向静态 `List<byte[]>` 追加 `new byte[512 * 1024]`（只增不减 → 模拟泄漏）
- 向静态 `List<String>` 追加 **1000 个** String

`512 * 1024` 里的 **1024 是 1 KB 的字节数**（2¹⁰）。数组长度是 **524288**，也就是 **512 KB**，写成乘法是为了读出「512 个 KB」，避免直接写 `524288`。

这和 G1 的 `region size 1024K` **不是同一个 1024**：`1024K` = 1024 × 1024 字节 = **1 MB**（一格 region）。代码里的 `1024` 只表示「1 KB = 1024 字节」。

因此 histo 里 **`[B` 和 `java.lang.String` 会涨**；`[B` 其实叠了两摊（大数组 + String 的小 `value`）。G1 下 `FillerArray` 也会涨，**不是**第三个业务字段，见 5.1。

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

- **`[B` 只表示 `byte[]`，不是 `String`**。这一行叠了两摊 `byte[]`：`LEAKED_BUFFERS` 的大数组，以及 `STRING_LEAK` 里每条 String 的 `value`（Java 9+ 字符数据在独立的 `byte[]` 上）。`String` 对象本身在下一行 `java.lang.String`，见 5.1
- **`java.lang.String` 实例数 ≈ round × 1000** → `STRING_LEAK` 的对象头 + 字段（不含 `value` 数组）
- **`FillerArray` 没有对应字段** → VM 填空洞；大块尾巴跟 `LEAKED_BUFFERS` 走，小块尾巴跟 String 的 TLAB 走
- 在真实故障里：若某业务 DTO、缓存 Map、char[] 长期霸榜 → 重点查对应代码路径

### 5.1 字段怎么对上 histo（本机 JDK 21.0.2 实测）

Demo 控制台当时：`leak round 108, buffers=108, strings=108000`。`jcmd GC.heap_info`：`region size 1024K`。紧接着 `jmap -histo`：

```
 num     #instances         #bytes  class name (module)
-------------------------------------------------------
   1:        122269       62666192  [B (java.base@21.0.2)
   2:           150       59611976  Ljdk.internal.vm.FillerArray; (java.base@21.0.2)
   3:        120976        2903424  java.lang.String (java.base@21.0.2)
```

**不要**做成「`LEAKED_BUFFERS` = `FillerArray`、`STRING_LEAK` = `String`」。三行和两个字段是这样拆的：

| 源码字段 | histo 里是谁 | 这份数怎么对 |
|----------|--------------|--------------|
| `LEAKED_BUFFERS` | **大块 `[B]`**（约 512 KB / 个） | `108 × 524288 ≈ 56.6 MB`；`[B]` 合计 62.7 MB，多出来的约 6 MB 是小数组 |
| `STRING_LEAK` | **`java.lang.String`** + **小块 `[B]`**（Latin-1 的 `value`） | String **120976 ≈ 108000 + JVM 自用**；小 `[B]` 个数 ≈ `122269 − 108 ≈ 12.2 万`，和 String 近 1:1；平均约 **50 字节**（`"leak-round-N-item-M"` 二十多个字符 + 数组头） |
| （没有字段） | **`FillerArray`** | VM 填的死空间，应用拿不到引用 |

`[B]` **不包括** `String` 类。histo 里 `java.lang.String` 和 `[B]` 是两行：前者是 String 对象（约 `2903424 / 120976 ≈ 24` 字节，对象头 + 字段，里面只有指向 `value` 的引用），后者全是 `byte[]`。Java 9+ 的 compact string 把字符放在 `value` 这块 `byte[]` 上，所以 `STRING_LEAK` 会**另外**贡献一行小 `[B]`。histo **不按长度拆** `[B]`，用 **`#bytes / #instances`** 分开：512 KB 量级是 `LEAKED_BUFFERS`，几十字节是 String 的 `value`。

#### `FillerArray` 为什么这份里也吃到 59 MB

`Ljdk.internal.vm.FillerArray;` 是 HotSpot 自己造的填充对象：空洞必须填平，堆才能按对象边界扫。JDK 16+ 从混在 `[I` 里拆出来，避免和业务 `int[]` 搞混。较新的 21 更新里名字可能是 `[Ljdk/internal/vm/FillerElement;`。

G1 把堆切成固定 **1 MB** 的 region（`region size 1024K`）。对象要放进格子里；一格装不满的部分不能空着给 `jmap` 扫，VM 就塞一个 `FillerArray` 把尾巴填平。

Demo 每次 `new byte[512 * 1024]`：`1024` = 1 KB 的字节数，长度 `524288` = **512 KB**（和 `region size 1024K` 那个「1024 个 KB = 1 MB」不是一回事）。有效载荷 512 KB，加上数组头还要再大一点。一格 1 MB 里：

```
|--------- 1 MB region ---------|
|  512 KB 的 [B]  |  剩下的空洞  |
                  ↑
            填成 FillerArray
```

两个 **512 KB 有效载荷** 加起来正好 1 MB，但堆里的对象比有效载荷大，一格仍然装不下两条。

`new byte[512 * 1024]` 只要 **524288** 字节的数据。HotSpot 64 位、压缩指针时，`byte[]` 还要：

- mark word 8 字节
- klass 4 字节
- 数组 `length` 4 字节

头一共 **16** 字节。对齐之后每个对象大约 **524304** 字节：

```
524304 × 2 = 1,048,608
1 MB      = 1,048,576
差额        = 32 字节
```

多出来的 32 字节已经越过 region 边界。普通对象又**不能骑在两格上**，所以一格只能放一条，剩下的半格用 `FillerArray` 填平。

G1 还把「大于半个 region」的对象当 **humongous**。半格是 524288，对象是 524304，**刚好过线**，往往会整格专给这一条数组，更不会在同一格里再塞第二条。要填充，不是 512+512 算错了，是 **没把对象头算进 1 MB**。

这份平均一块 `59611976 / 150 ≈ 388 KB`，和「半格 region」同一量级（不是整整 512 KB：还有对象头、对齐，以及少数 TLAB 小块把平均数拉低）。108 条大数组 ≈ 108 条这样的尾巴，所以 `FillerArray` 的 `#bytes`（59.6 MB）和 `[B]`（62.7 MB）会**一起爬**。59 MB 不是又泄漏了一份业务数据，是格子太大、数组只占一半的空隙被算进 histo。实例数只有 **150**，和 12 万 String 对不上。

String 走另一条路：每轮 1000 次 `"leak-round-"+…` 是小对象，走 **TLAB**。TLAB 用剩的尾巴也会变成 filler，块头小（常见几 KB），会拉开 filler **个数**，但通常拉不动这份这种「按字节排第二」的体量。

#### runtime 里 filler 算不算占内存

占的是**已经划进堆的那截地址**，不是 VM 在数组之外又向 OS 多要了一份。

G1 先拿出一整格 1 MB region（对 OS 来说已经算堆内存）。放进 512 KB 的 `[B]` 之后，格子里还剩半截。`FillerArray` 把这半截标成一个对象。histo 里的 59 MB 是这些尾巴上的**真实字节**，不是画出来的假数。

| | 算不算 |
|--|--------|
| 进程地址空间 / region 已提交 | 算。格子一领就是 1 MB，不管尾巴叫不叫 filler |
| 再向 OS 多要一份 388 KB | 不算。没有额外申请 |
| 应用还能用这半格再塞一个 512 KB 数组 | 不能。空间已被这格占用 |
| 活对象 / 你的代码能拿到引用 | 不能。GC 眼里是死空间 |

runtime **确实占着堆容量**（committed / `used` 里能看到），但**不是第三条泄漏在另开内存**。数组本身在 `[B]`，空隙挂名叫 `FillerArray`。

另一时刻（泄漏更久、或 mixed GC 收过 region 空洞）可能变成「filler 实例更多、总字节更小」，例如：

```
   1:        110538       67199272  [B
   2:        109778        2634672  java.lang.String
   3:           242        2000328  Ljdk.internal.vm.FillerArray;
```

`242 × 约 8 KB ≈ 2 MB`，这时 histo 里剩下的主要是 **String 刷 TLAB 留下的小块**。region 级大尾巴已经不在榜上了。

本机对同一进程立刻跑 `jmap -histo:live`，`[B]` / `String` / `FillerArray` 都几乎没掉（filler 仍约 59 MB）。`:live` 的 Young/Full 不一定搬走 old 里的 region 空洞；G1 后续 mixed 才可能把这些 filler 减下去。所以 **`:live` 后 filler 还在，也不能把它当成第三条强引用泄漏**。

| | `LEAKED_BUFFERS` | `STRING_LEAK` | `FillerArray` |
|--|------------------|---------------|---------------|
| 谁 new | Demo | Demo | VM / GC |
| histo | 大 `[B]` | `String` + 小 `[B]` | 单独一类 |
| 跟谁涨 | 每 500ms +1 个 512 KB | 每轮 +1000 | 大块跟 region 走，小块跟 TLAB 走 |
| 去业务代码查吗 | 要 | 要 | 不要 |

排障：盯 `[B]`（看平均大小拆两摊）和 `String`。不要给 `FillerArray` 找一个不存在的 `LEAKED_BUFFERS` 对应。

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
  jmap -histo "$PID" | grep -E '^\s+[0-9]+:.*(\[B|java.lang.String|FillerArray|FillerElement)'
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
| 第三条是 `FillerArray` / `FillerElement` 且跟着涨 | VM 填 G1/TLAB 空洞，不是业务泄漏；见 5.1 |
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
- 总计划（第 5 日）：`docs/jdk-toolkit-learning-plan.md`
- 线程排查：`docs/jstack-troubleshooting-guide.md`
- GC 趋势：`docs/jstat-troubleshooting-guide.md`
- `jmap -heap` 替代：`docs/jcmd-troubleshooting-guide.md`、`docs/jhsdb-troubleshooting-guide.md`
- 堆转储分析：`docs/mat-troubleshooting-guide.md`
