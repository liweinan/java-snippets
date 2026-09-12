# jhsdb 事后 / 低级 attach 实操手册

本手册基于 `io.weli.concurrent.JvmToolkitDemo`，**已在本机（JDK 21 / macOS）验证命令存在，并复现了活进程 attach 失败**。

总计划：`docs/jdk-toolkit-learning-plan.md`（第 9 日）。

`jhsdb` 是 Serviceability Agent。活进程优先 `jcmd`；这里解决的是 **旧 `jmap -heap` 不可用**、**core dump**、或 **SA 级堆/线程查看**。

---

## 一、子命令

```bash
jhsdb --help
```

| 子命令 | 用途 |
|--------|------|
| `jhsdb jmap --heap --pid <pid>` | 堆配置（JDK 21 上替代 `jmap -heap`） |
| `jhsdb jmap --pid <pid>` | 直方图等（见 `jhsdb jmap --help`） |
| `jhsdb jstack --pid <pid>` | SA 路径抓栈 |
| `jhsdb jinfo --pid <pid>` | SA 路径看标志 |
| `jhsdb hsdb` | SA GUI |
| `jhsdb clhsdb` | SA 命令行 |

对 core：把 `--pid` 换成 `--core <core文件> --exe <java二进制>`。

---

## 二、先用活进程命令（推荐）

```bash
PID=$(jps -l | grep JvmToolkitDemo | awk '{print $1}')
jcmd "$PID" GC.heap_info
jcmd "$PID" VM.flags
```

本机 `GC.heap_info` 已成功（见 `docs/jcmd-troubleshooting-guide.md`）。能走 `jcmd` 就不要上 `jhsdb`。

---

## 三、本机实测：macOS 活进程 attach 失败

Demo 在跑、`jcmd` 正常时：

```bash
jhsdb jmap --heap --pid 65237
```

```
Attaching to process ID 65237, please wait...
ERROR: attach: task_for_pid(65237) failed: '(os/kern) failure' (5)
Error attaching to process: Can't attach to the process.
```

原因：Serviceability Agent 走 `task_for_pid`，macOS SIP / 权限模型经常直接拒绝。`sudo` 也不一定够。

**过关标准**：知道这是环境限制，改回 `jcmd`；不要把失败当成 Demo 没启动。

Linux 上同一条 `jhsdb jmap --heap --pid` 通常可用，也是 `jmap -heap` 报 `Use jhsdb jmap instead` 时的官方退路。

---

## 四、什么时候值得用

| 情况 | 做法 |
|------|------|
| 活进程，只要堆摘要 | `jcmd GC.heap_info` |
| Linux 上 `jmap -heap` 报错 | `jhsdb jmap --heap --pid` |
| 只有 core、进程已死 | `jhsdb jstack/jmap --core ... --exe ...` |
| macOS 活进程 SA 失败 | 停在 `jcmd` / `jmap -histo` / `jmap -dump` |

---

## 五、相关文件

- 示例代码：`src/main/java/io/weli/concurrent/JvmToolkitDemo.java`
- `jmap -heap` 失败说明：`docs/jmap-troubleshooting-guide.md` 第六节
- 总计划：`docs/jdk-toolkit-learning-plan.md`
