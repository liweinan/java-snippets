# jstat GC 采样实操手册

本手册基于 `io.weli.concurrent.JvmToolkitDemo`，**已在本机（JDK 21）实际跑通**。

总计划：`docs/jdk-toolkit-learning-plan.md`（第 3 日）。

---

## 一、jstat 能干什么

对活进程做 **周期性计数器采样**（不 dump 堆、不暂停很久）。适合回答：

- Young GC 是否在狂跑
- Old 区是否只增不减
- Full GC 有没有出现

| 命令 | 用途 |
|------|------|
| `jstat -gcutil <pid> 1000 10` | 各区占用百分比 + GC 次数/耗时（最常用） |
| `jstat -gc <pid>` | 各区容量/已用绝对值（KB） |
| `jstat -gccause <pid>` | 最近一次 / 当前 GC 原因 |
| `jstat -class <pid>` | 类加载数 |

间隔和次数：`jstat -gcutil <pid> <间隔毫秒> <次数>`。

---

## 二、Demo 在模拟什么

`JvmToolkitDemo` 的 `young-allocator` 每轮分配若干 **64KB byte[] 且不保留引用**。

因此应看到：

- `YGC` 持续增加
- `O`（Old 占用百分比）基本不动
- `FGC` 保持 0

这和 `JmapHeapDemo`（static 集合只增不减 → Old / 已用堆爬升）正好相反。

---

## 三、启动 Demo

```bash
cd /path/to/java-snippets
mvn -q compile -DskipTests
java -Xms64m -Xmx128m -XX:+UseG1GC \
  -Dtoolkit.demo.name=JvmToolkitDemo \
  -cp target/classes io.weli.concurrent.JvmToolkitDemo
```

```bash
PID=$(jps -l | grep JvmToolkitDemo | awk '{print $1}')
```

---

## 四、读 `gcutil`（本机实测）

```bash
jstat -gcutil "$PID" 1000 5
```

```
  S0     S1     E      O      M     CCS    YGC     YGCT     FGC    FGCT     CGC    CGCT       GCT
     -  45.54  28.21   3.89  75.32  34.18      8     0.006     0     0.000     0     0.000     0.006
     -  44.15  30.77   3.89  75.32  34.18      9     0.007     0     0.000     0     0.000     0.007
     -  43.28  41.03   3.89  75.32  34.18     10     0.009     0     0.000     0     0.000     0.009
     -  42.22  43.59   3.89  75.32  34.18     11     0.010     0     0.000     0     0.000     0.010
     -  42.70  53.85   3.89  75.32  34.18     12     0.011     0     0.000     0     0.000     0.011
```

| 列 | 含义 | 本 Demo |
|----|------|---------|
| `E` | Eden 占用 % | 来回涨，触发 Young GC |
| `O` | Old 占用 % | 一直约 `3.89`，没有泄漏 |
| `YGC` / `YGCT` | Young GC 次数 / 累计秒 | 5 秒内 8→12 |
| `FGC` / `FGCT` | Full GC 次数 / 累计秒 | 0 |
| `GCT` | 所有 GC 累计秒 | 约等于 `YGCT` |
| `S0`/`S1` | Survivor | G1 下 `S0` 常为 `-` |
| `M` / `CCS` | Metaspace / Compressed Class Space % | 本 Demo 几乎不变 |

结论：在分配，但对象活不过 Young 代。

绝对值对照：

```bash
jstat -gc "$PID"
```

`EU`（Eden used）和 `YGC` 与上面一致；`OU`（Old used）约 1MB 量级。

---

## 五、和泄漏 Demo 对比

另开终端启动 `JmapHeapDemo`（`-Xmx256m`），对它的 PID 再跑同样的 `jstat -gcutil`：

- `O` 或已用堆会随泄漏轮次上升
- 最终可能出现 `FGC` / 分配变慢

不要两个 Demo 同时 `grep Demo` 混 PID。

---

## 六、和 jmap / jcmd 怎么分工

| 问题 | 工具 |
|------|------|
| 接下来 30 秒 GC 趋势 | `jstat` |
| 此刻堆配置 / 已用 | `jcmd GC.heap_info` |
| 谁占用了字节 | `jmap -histo` |

先 `jstat` 确认「在恶化」，再 `jmap` 看「是谁」。

---

## 七、常见问题

| 问题 | 处理 |
|------|------|
| 列对不上旧教程 | G1 / ZGC 列名和 PermGen 时代不同，以表头为准 |
| `YGC` 不动 | Demo 刚启动或分配太慢；等几秒 |
| 采样本身很重？ | 比 dump 轻得多；仍不要对超大集群秒级狂打 |

---

## 八、一条龙

```bash
PID=$(jps -l | grep JvmToolkitDemo | awk '{print $1}')
jstat -gcutil "$PID" 1000 10
jcmd "$PID" GC.heap_info
```

---

## 九、相关文件

- 示例代码：`src/main/java/io/weli/concurrent/JvmToolkitDemo.java`
- 泄漏对照：`src/main/java/io/weli/concurrent/JmapHeapDemo.java`
- 总计划：`docs/jdk-toolkit-learning-plan.md`
- 下一步：`docs/jstack-troubleshooting-guide.md`
