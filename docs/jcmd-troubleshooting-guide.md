# jcmd 总枢纽实操手册

本手册基于 `io.weli.concurrent.JvmToolkitDemo`，**已在本机（JDK 21）实际跑通**。

总计划：`docs/jdk-toolkit-learning-plan.md`（第 2 / 7 日）。

`jcmd` 是 JDK 7+ 的统一入口。旧工具（`jmap` / `jstack` / `jinfo`）在现场仍常用；JDK 21 上部分旧选项（尤其 `jmap -heap`）会失败，改走这里。

---

## 一、先列出这个进程支持什么

```bash
PID=$(jps -l | grep JvmToolkitDemo | awk '{print $1}')
jcmd "$PID" help
```

本机（JDK 21）可用命令包括：`VM.flags`、`VM.command_line`、`VM.system_properties`、`VM.uptime`、`GC.heap_info`、`GC.class_histogram`、`GC.heap_dump`、`Thread.print`、`JFR.start` 等。

单条帮助：

```bash
jcmd "$PID" help GC.heap_info
```

---

## 二、和旧工具对照

| 旧命令 | jcmd | 本仓库手册 |
|--------|------|------------|
| `jstack <pid>` | `jcmd <pid> Thread.print` | `jstack-troubleshooting-guide.md` |
| `jmap -histo <pid>` | `jcmd <pid> GC.class_histogram` | `jmap-troubleshooting-guide.md` |
| `jmap -heap <pid>` | `jcmd <pid> GC.heap_info` | 同上（JDK 21 请用 jcmd） |
| `jmap -dump:live,format=b,file=x.hprof` | `jcmd <pid> GC.heap_dump /abs/path.hprof` | 同上 |
| `jinfo -flags` | `jcmd <pid> VM.flags` | `jinfo-troubleshooting-guide.md` |
| `jinfo -sysprops` | `jcmd <pid> VM.system_properties` | 同上 |

`jstat` 没有完整等价物：要连续采样仍用 `jstat`。

---

## 三、本 Demo 实测片段

启动方式见 `docs/jps-troubleshooting-guide.md`。

```bash
jcmd "$PID" VM.uptime
# 12.362 s

jcmd "$PID" VM.command_line
# jvm_args: -Xms64m -Xmx128m -XX:+UseG1GC -Dtoolkit.demo.name=JvmToolkitDemo

jcmd "$PID" GC.heap_info
```

```
 garbage-first heap   total 67584K, used 22552K [0x..., 0x...)
  region size 1024K, 22 young (22528K), 1 survivors (1024K)
 Metaspace       used 931K, committed 1088K, reserved 1114112K
```

```bash
jcmd "$PID" Thread.print | grep -A3 young-allocator
```

```
"young-allocator" #28 ... daemon ...
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at io.weli.concurrent.JvmToolkitDemo.sleepQuietly(JvmToolkitDemo.java:59)
```

系统属性过滤：

```bash
jcmd "$PID" VM.system_properties | grep toolkit.demo
# toolkit.demo.name=JvmToolkitDemo
```

---

## 四、建议记住的最小集合

1. `help` —— 先看这个 JDK / 这个进程有什么
2. `VM.command_line` + `VM.flags` —— 启动对不对
3. `GC.heap_info` —— 堆用了多少
4. `Thread.print` —— 线程
5. `GC.class_histogram` / `GC.heap_dump` —— 内存（dump 会 STW）

---

## 五、常见问题

| 问题 | 处理 |
|------|------|
| `Unknown diagnostic command` | `help` 列表为准，版本不同命令不同 |
| dump 路径无效 | `GC.heap_dump` 要用**绝对路径** |
| 和 `jstack` 输出略有差别 | 正常；看线程名和 `Thread.State` 即可 |

---

## 六、一条龙

```bash
PID=$(jps -l | grep JvmToolkitDemo | awk '{print $1}')
jcmd "$PID" help
jcmd "$PID" VM.command_line
jcmd "$PID" GC.heap_info
jcmd "$PID" Thread.print | grep young-allocator
```

---

## 七、相关文件

- 示例代码：`src/main/java/io/weli/concurrent/JvmToolkitDemo.java`
- 总计划：`docs/jdk-toolkit-learning-plan.md`
- 参数核对：`docs/jinfo-troubleshooting-guide.md`
