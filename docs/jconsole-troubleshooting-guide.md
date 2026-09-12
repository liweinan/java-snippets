# jconsole 本机观察实操手册

本手册基于 `io.weli.concurrent.JvmToolkitDemo`，**已在本机（JDK 21）确认 `jconsole` 随 JDK 提供**。

总计划：`docs/jdk-toolkit-learning-plan.md`（第 8 日）。

`jconsole` 是 JMX 客户端，适合看 **实时曲线**。深度泄漏仍走 `jmap` + MAT。

---

## 一、只做本机 attach

同一用户、本机进程：直接：

```bash
PID=$(jps -l | grep JvmToolkitDemo | awk '{print $1}')
jconsole "$PID"
```

或打开 `jconsole`，在本地进程列表里选 `io.weli.concurrent.JvmToolkitDemo`。

**不要**为了练习打开无认证远程 JMX（`-Dcom.sun.management.jmxremote.authenticate=false`）。那是把管理口暴露到网络。

---

## 二、启动 Demo

```bash
cd /path/to/java-snippets
mvn -q compile -DskipTests
java -Xms64m -Xmx128m -XX:+UseG1GC \
  -Dtoolkit.demo.name=JvmToolkitDemo \
  -cp target/classes io.weli.concurrent.JvmToolkitDemo
```

---

## 三、四个页签看什么

| 页签 | 本 Demo 应看到 |
|------|----------------|
| **Overview** | 堆使用呈锯齿（Eden 填满 → Young GC → 回落） |
| **Memory** | Eden / Survivor 抖动；Old 几乎平 |
| **Threads** | `young-allocator`、`main`（`TIMED_WAITING`） |
| **VM Summary** | `-Xmx128m`、`UseG1GC`，与 `jcmd VM.command_line` 一致 |

对照第 3 日的 `jstat -gcutil`：图上的锯齿就是那里的 `E` + `YGC`。

泄漏对照：对 `JmapHeapDemo` 做同样观察，Old / Heap 会单边上扬。

---

## 四、和 VisualVM

JDK 21 本机 **不再自带** `jvisualvm`（`which jvisualvm` 为空）。需要时单独安装 VisualVM，用法与 jconsole 类似：本机 attach → Monitor / Sampler。堆转储分析优先 MAT。

---

## 五、常见问题

| 问题 | 处理 |
|------|------|
| 本地列表没有目标进程 | 同用户启动；先 `jps -l` 确认 |
| 连接被拒 | 不要改去开远程 JMX；检查是否 attach 了别的 JDK 版本的进程 |
| 曲线是平的 | Demo 刚启动或分配线程已停；看控制台是否还在打 `young-alloc round` |

---

## 六、相关文件

- 示例代码：`src/main/java/io/weli/concurrent/JvmToolkitDemo.java`
- 总计划：`docs/jdk-toolkit-learning-plan.md`
- GC 数字对照：`docs/jstat-troubleshooting-guide.md`
- 堆深度分析：`docs/mat-troubleshooting-guide.md`
