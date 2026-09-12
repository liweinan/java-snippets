# jinfo / VM 参数核对着实操手册

本手册基于 `io.weli.concurrent.JvmToolkitDemo`，**已在本机（JDK 21）实际跑通**。

总计划：`docs/jdk-toolkit-learning-plan.md`（第 2 日）。

JDK 21 上优先用 **`jcmd VM.*`**；`jinfo` 仍常见，二者对照如下。

---

## 一、能看什么

| 问题 | 推荐命令 |
|------|----------|
| 完整启动命令 | `jcmd <pid> VM.command_line` |
| 生效的 `-XX` 标志 | `jcmd <pid> VM.flags` 或 `jinfo -flags <pid>` |
| 单个标志 | `jinfo -flag MaxHeapSize <pid>` |
| 系统属性 | `jcmd <pid> VM.system_properties` 或 `jinfo -sysprops <pid>` |

不要靠记忆「当时是怎么启动的」——以进程里实际生效的值为准。

---

## 二、启动 Demo

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

## 三、命令行与标志（本机实测）

```bash
jcmd "$PID" VM.command_line
```

```
VM Arguments:
jvm_args: -Xms64m -Xmx128m -XX:+UseG1GC -Dtoolkit.demo.name=JvmToolkitDemo
java_command: io.weli.concurrent.JvmToolkitDemo
java_class_path (initial): target/classes
Launcher Type: SUN_STANDARD
```

```bash
jcmd "$PID" VM.flags
# 或
jinfo -flags "$PID"
```

本机片段：

```
-XX:InitialHeapSize=67108864 -XX:MaxHeapSize=134217728 -XX:+UseG1GC ...
```

`MaxHeapSize=134217728` = 128MB，与 `-Xmx128m` 一致；`InitialHeapSize` 对应 `-Xms64m`。

单个标志：

```bash
jinfo -flag MaxHeapSize "$PID"
# -XX:MaxHeapSize=134217728
```

---

## 四、系统属性

```bash
jcmd "$PID" VM.system_properties | grep toolkit.demo
# toolkit.demo.name=JvmToolkitDemo
```

`jinfo -sysprops` 输出等价，只是入口不同。

本 Demo 用 `-Dtoolkit.demo.name` 当「可搜索的标记」：现场可以用同样方式给应用打环境标签（`env=staging` 等），再用这里确认有没有打上。

---

## 五、jinfo vs jcmd

| 场景 | 用谁 |
|------|------|
| 日常核对 | `jcmd`（JDK 21 更稳） |
| 同事只记得 jinfo | `jinfo -flag` / `-sysprops` 即可 |
| 改运行中的 manage-able 标志 | `jcmd <pid> VM.set_flag <name> <value>`（仅部分标志可改；改错会伤进程） |

本练习只读，不改标志。

---

## 六、常见问题

| 问题 | 处理 |
|------|------|
| attach 失败 | 同用户；macOS 可试 `sudo`；确认 `jps` 能看到该 PID |
| 输出里没有你设的 `-D` | 属性名拼错，或启动的不是这个进程 |
| `MaxHeapSize` 和你想的不一致 | Ergonomics 可能放大/缩小；以 `VM.flags` 为准 |

---

## 七、相关文件

- 示例代码：`src/main/java/io/weli/concurrent/JvmToolkitDemo.java`
- 总计划：`docs/jdk-toolkit-learning-plan.md`
- 姊妹篇：`docs/jcmd-troubleshooting-guide.md`
- 下一步：`docs/jstat-troubleshooting-guide.md`
