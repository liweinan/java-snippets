# jps 进程定位实操手册

本手册基于 `io.weli.concurrent.JvmToolkitDemo`，**已在本机（JDK 21）实际跑通**。

总计划：`docs/jdk-toolkit-learning-plan.md`（第 1 日）。

---

## 一、jps 能干什么

列出本机 HotSpot 进程。排查的第一步永远是 **PID 不要抓错**。

| 命令 | 输出 |
|------|------|
| `jps` | PID + 短类名 |
| `jps -l` | PID + 主类全名 / jar 路径 |
| `jps -v` | 额外打印 JVM 参数（`-Xmx`、`-XX:`、`-D`） |
| `jps -m` | 额外打印传给 `main` 的参数 |
| `jps -lvm` | 全名 + 启动参数（最常用） |
| `jps -q` | 只输出 PID |

`jps` 自己也会出现在列表里（`jdk.jcmd/sun.tools.jps.Jps`），可忽略。

---

## 二、启动 Demo

```bash
cd /path/to/java-snippets
mvn -q compile -DskipTests
java -Xms64m -Xmx128m -XX:+UseG1GC \
  -Dtoolkit.demo.name=JvmToolkitDemo \
  -cp target/classes io.weli.concurrent.JvmToolkitDemo
```

输出示例：

```
JvmToolkitDemo started, PID = 65237
  toolkit.demo.name = JvmToolkitDemo
```

**另开终端**操作。

---

## 三、实际输出

```bash
jps -l | grep JvmToolkitDemo
# 65237 io.weli.concurrent.JvmToolkitDemo

jps -lvm | grep JvmToolkitDemo
# 65237 io.weli.concurrent.JvmToolkitDemo -Xms64m -Xmx128m -XX:+UseG1GC -Dtoolkit.demo.name=JvmToolkitDemo
```

读法：

1. 第一列是 PID
2. `-l` 确认主类，避免同名 jar / 短类名撞车
3. `-v` 核对这次启动是否带了你以为的 `-Xmx` / `-D`

---

## 四、和其他找进程方式

```bash
ps aux | grep JvmToolkitDemo
lsof -i :18080          # 已知监听端口时（例如 JmeterTargetServer）
```

`jps` 只看 JVM；`ps` / `lsof` 在进程不是 Java、或 jps 因权限看不到时有用。

---

## 五、常见问题

| 问题 | 处理 |
|------|------|
| 列表是空的 | 不是同一用户；或目标不是 HotSpot |
| 只有 PID 没有主类 | 进程已经退出 main、或是 native launcher；改用 `ps -p <pid> -o args` |
| 抓错 PID | 用 `-l` 对全名，不要只对短名 `grep Demo` |
| `jps: command not found` | `$JAVA_HOME/bin/jps`，与目标进程同一套 JDK |

---

## 六、清理

```bash
jps -l | grep JvmToolkitDemo | awk '{print $1}' | xargs kill
```

---

## 七、相关文件

- 示例代码：`src/main/java/io/weli/concurrent/JvmToolkitDemo.java`
- 总计划：`docs/jdk-toolkit-learning-plan.md`
- 下一步：`docs/jinfo-troubleshooting-guide.md`
