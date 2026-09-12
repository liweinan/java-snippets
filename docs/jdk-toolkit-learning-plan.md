# JDK 诊断工具集学习计划

按**线上排查顺序**把仓库里的 JDK 工具串起来，而不是按字母表。每一步都有可运行 Demo 和分册手册。

本机验证环境：**JDK 21.0.2**。

---

## 一、为什么是这个顺序

排查永远是「先锁定进程 → 看它怎么启动 → 看它此刻在干什么 → 再决定 dump」。

```
jps 锁定 PID
  → jinfo / jcmd VM.* 核对启动参数
  → jstat 看堆和 GC 是否在恶化
  → 线程问题走 jstack；内存问题走 jmap
  → 需要图或引用链：jconsole / MAT
  → 活进程 attach 失败、或只有 core：jhsdb
```

`jcmd` 是总枢纽：上面多数能力它都能做一份，旧工具名（`jmap` / `jstack` / `jinfo`）在手册里保留，因为现场更常见。

---

## 二、症状 → 工具

| 你看到的现象 | 先用 | 再用 | Demo |
|--------------|------|------|------|
| 机器上有一堆 Java，不知道打谁 | `jps -l` / `jps -lvm` | `ps` / `lsof` | `JvmToolkitDemo` |
| 怀疑 `-Xmx`、GC、系统属性不对 | `jcmd VM.flags` / `VM.command_line` | `jinfo -flag` / `-sysprops` | `JvmToolkitDemo` |
| GC 频繁、Young 涨、Old 是否泄漏 | `jstat -gcutil <pid> 1000` | `jcmd GC.heap_info` | `JvmToolkitDemo`（Young GC）或 `JmapHeapDemo`（泄漏） |
| CPU 高、卡住、疑似死锁 | `jstack` / `jcmd Thread.print` | 连续抓 3 次 | `JstackTroubleDemo` |
| 堆占用高、OOM、直方图 | `jmap -histo` | `jmap -dump` → MAT | `JmapHeapDemo` |
| 要看实时曲线 | `jconsole <pid>` | — | `JvmToolkitDemo` |
| `jmap -heap` 报错、或只有 core | `jcmd GC.heap_info` | `jhsdb jmap --heap --pid` | `JvmToolkitDemo` |

---

## 三、仓库里有什么

| 角色 | 类 / 手册 |
|------|-----------|
| 轻量靶进程（jps / jstat / jinfo / jcmd / jconsole / jhsdb） | `io.weli.concurrent.JvmToolkitDemo` |
| 线程故障 | `JstackTroubleDemo` + `docs/jstack-troubleshooting-guide.md` |
| 堆泄漏 | `JmapHeapDemo` + `docs/jmap-troubleshooting-guide.md` |
| 堆转储深度分析 | `docs/mat-troubleshooting-guide.md` + `mat/*.sh` |
| 压测（外围，不是 JDK 自带工具） | `JmeterTargetServer` + `docs/jmeter-troubleshooting-guide.md` |

分册：

- [`jps-troubleshooting-guide.md`](jps-troubleshooting-guide.md)
- [`jinfo-troubleshooting-guide.md`](jinfo-troubleshooting-guide.md)
- [`jstat-troubleshooting-guide.md`](jstat-troubleshooting-guide.md)
- [`jcmd-troubleshooting-guide.md`](jcmd-troubleshooting-guide.md)
- [`jstack-troubleshooting-guide.md`](jstack-troubleshooting-guide.md)
- [`jmap-troubleshooting-guide.md`](jmap-troubleshooting-guide.md)
- [`mat-troubleshooting-guide.md`](mat-troubleshooting-guide.md)
- [`jconsole-troubleshooting-guide.md`](jconsole-troubleshooting-guide.md)
- [`jhsdb-troubleshooting-guide.md`](jhsdb-troubleshooting-guide.md)

---

## 四、十日计划

每天只练一类决策。前三天共用 `JvmToolkitDemo`，不要同时开泄漏 Demo，以免直方图和 GC 曲线互相干扰。

### 第 1 日：jps —— 锁定进程

目标：从本机多个 JVM 里准确取出 PID，并读出主类和启动参数。

```bash
cd /path/to/java-snippets
mvn -q compile -DskipTests
java -Xms64m -Xmx128m -XX:+UseG1GC \
  -Dtoolkit.demo.name=JvmToolkitDemo \
  -cp target/classes io.weli.concurrent.JvmToolkitDemo
```

另开终端：

```bash
jps -l
jps -lvm | grep JvmToolkitDemo
```

过关：能指出 PID，且 `-lvm` 里能看到 `-Xmx128m` 和 `-Dtoolkit.demo.name`。

手册：`docs/jps-troubleshooting-guide.md`

### 第 2 日：jinfo / jcmd VM.* —— 核对启动参数

目标：不重启进程，确认堆上限、GC、自定义系统属性。

```bash
PID=$(jps -l | grep JvmToolkitDemo | awk '{print $1}')
jcmd "$PID" VM.command_line
jcmd "$PID" VM.flags
jcmd "$PID" VM.system_properties | grep toolkit.demo
jinfo -flag MaxHeapSize "$PID"
```

过关：`MaxHeapSize=134217728`（128MB），`toolkit.demo.name=JvmToolkitDemo`。

手册：`docs/jinfo-troubleshooting-guide.md`、`docs/jcmd-troubleshooting-guide.md`

### 第 3 日：jstat —— 看 GC 是否在恶化

目标：连续采样，区分「Young 正常回收」和「Old 只增不减」。

```bash
jstat -gcutil "$PID" 1000 10
```

`JvmToolkitDemo` 只分配短生命周期对象：`YGC` 会升，`O`（Old）应接近不动，`FGC` 保持 0。

对比：另开 `JmapHeapDemo` 再采一次，Old / 已用堆会持续爬升。

手册：`docs/jstat-troubleshooting-guide.md`

### 第 4 日：jstack —— 线程

关掉 Toolkit Demo，改开 `JstackTroubleDemo`。过关：能指认死锁、锁阻塞、忙等三条线程。

手册：`docs/jstack-troubleshooting-guide.md`

### 第 5 日：jmap —— 堆直方图与 dump

开 `JmapHeapDemo`。过关：`jmap -histo` 里 `[B` 和 `java.lang.String` 随轮次上涨；会做一次 `:live` 对比。

手册：`docs/jmap-troubleshooting-guide.md`

### 第 6 日：MAT —— 引用链

用第 5 日的 `.hprof`。过关：Leak Suspects 指向 `JmapHeapDemo` 的 static 集合，并能画出 Path to GC Roots。

手册：`docs/mat-troubleshooting-guide.md`

### 第 7 日：jcmd —— 一张对照表吃掉旧工具

回到 `JvmToolkitDemo`，把前几天的命令用 `jcmd` 各做一遍：

| 旧命令 | jcmd |
|--------|------|
| `jstack` | `Thread.print` |
| `jmap -histo` | `GC.class_histogram` |
| `jmap -heap`（JDK 21 常挂） | `GC.heap_info` |
| `jmap -dump` | `GC.heap_dump <file>` |
| `jinfo -flags` | `VM.flags` |
| `jinfo -sysprops` | `VM.system_properties` |

手册：`docs/jcmd-troubleshooting-guide.md`

### 第 8 日：jconsole —— 本机实时图

```bash
jconsole "$PID"
```

过关：Memory 里 Eden 锯齿、Threads 里能看到 `young-allocator`。不要开无认证的远程 JMX。

手册：`docs/jconsole-troubleshooting-guide.md`

### 第 9 日：jhsdb —— 活进程走不通时的退路

先继续用 `jcmd`。再试：

```bash
jhsdb jmap --heap --pid "$PID"
```

macOS 上常因 `task_for_pid` / SIP 失败（本机已复现）。过关：知道它解决什么问题、以及失败时改走 `jcmd`。

手册：`docs/jhsdb-troubleshooting-guide.md`

### 第 10 日：综合演练

不看手册标题，只根据症状选工具：

1. 「有三个 Java 进程，哪个是泄漏 Demo？」→ `jps -l`
2. 「这个进程 `-Xmx` 是多少？」→ `jcmd VM.flags`
3. 「是不是在疯狂 Young GC？」→ `jstat -gcutil`
4. 「线程是不是死锁了？」→ `jstack`
5. 「谁占了堆？」→ `jmap -histo`，需要引用链再 MAT

---

## 五、一条龙（前三日共用进程）

```bash
cd /path/to/java-snippets
mvn -q compile -DskipTests
java -Xms64m -Xmx128m -XX:+UseG1GC \
  -Dtoolkit.demo.name=JvmToolkitDemo \
  -cp target/classes io.weli.concurrent.JvmToolkitDemo &
sleep 2
PID=$(jps -l | grep JvmToolkitDemo | awk '{print $1}')
echo "PID=$PID"
jps -lvm | grep JvmToolkitDemo
jcmd "$PID" VM.command_line
jstat -gcutil "$PID" 1000 5
jcmd "$PID" GC.heap_info
jcmd "$PID" Thread.print | grep young-allocator
kill "$PID"
```

---

## 六、清理

```bash
jps -l | grep -E 'JvmToolkitDemo|JmapHeapDemo|JstackTroubleDemo' | awk '{print $1}' | xargs kill 2>/dev/null
```
