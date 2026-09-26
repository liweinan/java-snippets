# jstack 线程问题排查实操手册

本手册基于仓库中的 `io.weli.concurrent.JstackTroubleDemo`，**已在本机实际跑通并抓取 jstack 输出**。

---

## 一、你会排查的三类问题

| 类型 | 线程名 | 现象 | jstack 关键特征 |
|------|--------|------|-----------------|
| 死锁 | `deadlock-thread-1/2` | 两个线程互相等锁，永远卡住 | 末尾 `Found one Java-level deadlock` |
| 阻塞 | `blocked-thread` | 等别人持有的 **`synchronized` 监视器** | `BLOCKED (on object monitor)` + `waiting to lock` |
| 死循环 | `busy-loop-thread` | CPU 飙高 | `RUNNABLE` 且 `cpu=` 时间远大于其他线程 |

---

## 二、环境准备

```bash
# 确认 JDK 工具可用（与运行 Java 程序同一套 JDK）
java -version
jps -help
jstack -help
```

要求：`jstack` 与目标进程 **同一 JDK 版本**（否则 attach 可能失败）。

---

## 三、启动「有问题」的 Demo

### 1. 编译

```bash
cd /path/to/java-snippets
mvn compile -DskipTests
```

### 2. 前台运行（推荐第一次练习）

```bash
java -cp target/classes io.weli.concurrent.JstackTroubleDemo
```

控制台会打印 PID，例如：

```
JstackTroubleDemo started, PID = 14422
Run: jstack 14422
```

**不要关这个终端**，保持进程存活。

### 3. 后台运行（可选）

```bash
nohup java -cp target/classes io.weli.concurrent.JstackTroubleDemo > /tmp/jstack-demo.log 2>&1 &
```

---

## 四、找到目标 Java 进程

**另开一个终端**：

```bash
jps -l | grep JstackTroubleDemo
```

输出示例：

```
14422 io.weli.concurrent.JstackTroubleDemo
```

记下第一列 PID（本例 `14422`）。

其他方式：

```bash
# macOS / Linux
ps aux | grep JstackTroubleDemo

# 若知道端口（Web 应用常用）
lsof -i :8080
```

---

## 五、抓取线程栈（核心步骤）

```bash
jstack 14422 > /tmp/jstack-demo.txt
cat /tmp/jstack-demo.txt
```

等价命令（JDK 7+）：

```bash
jcmd 14422 Thread.print > /tmp/jstack-demo.txt
```

**生产建议**：连续抓 3 次，间隔 5 秒，对比栈是否变化：

```bash
for i in 1 2 3; do
  echo "===== dump $i $(date) =====" >> /tmp/jstack-demo.txt
  jstack 14422 >> /tmp/jstack-demo.txt
  sleep 5
done
```

- 栈不变 + `RUNNABLE` 在同一行 → 可能是死循环（网络 read 也可能是 `RUNNABLE`，要看栈顶）
- 栈一直 `BLOCKED` → 在等 `synchronized` 监视器
- 栈一直 `WAITING` / `TIMED_WAITING` → `wait` / `park` / `sleep` / `join` 等，**不是** `BLOCKED`
- 末尾出现 deadlock → 直接定位

---

## 六、逐段读 jstack 输出

### 6.1 单线程块长什么样

```
"deadlock-thread-1" #20 [25347] prio=5 os_prio=31 cpu=0.57ms elapsed=15.64s tid=0x... nid=25347 waiting for monitor entry  [0x...]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at io.weli.concurrent.JstackTroubleDemo.lambda$startDeadlock$0(JstackTroubleDemo.java:36)
	- waiting to lock <0x000000070fe13e80> (a java.lang.Object)
	- locked <0x000000070fe13e70> (a java.lang.Object)
	at java.lang.Thread.run(java.base@21/Thread.java:1583)
```

读法：

1. **第一行引号内** → 线程名（代码里 `new Thread(..., "deadlock-thread-1")`）
2. **`Thread.State`** → 线程状态（下面有对照表）
3. **`at ...`** → 调用栈，**最上面是当前卡住的地方**
4. **`waiting to lock`** → 正在等的锁
5. **`locked`** → 已经持有的锁（死锁排查关键）

### 6.2 线程状态对照

| jstack 状态 | 含义 | 常见原因 |
|-------------|------|----------|
| `RUNNABLE` | 正在运行或等 CPU | 正常计算、忙等死循环；**网络 read / 磁盘 IO 也常显示 RUNNABLE** |
| `BLOCKED` | 等进入 `synchronized`（对象监视器） | 监视器被别的线程占用，见 6.3 |
| `WAITING` | 无限期等待 | `Object.wait()`、`LockSupport.park()`、`join()`；`ReentrantLock.lock()` 也多半是这个 |
| `TIMED_WAITING` | 限时等待 | `Thread.sleep()`、`wait(timeout)`、`tryLock(timeout)` |

### 6.3 `BLOCKED` 只等监视器，不是所有「卡住」

`BLOCKED (on object monitor)` **只**表示：这条线程要进 `synchronized`，锁被别人占着，它在等对方退出同步块把监视器放掉。旁边会有 `waiting to lock <地址>`。

下面这些**不是** `BLOCKED`：

| 实际在等什么 | 常见状态 |
|--------------|----------|
| `Object.wait()` / `join()` / `LockSupport.park()` | `WAITING` |
| `Thread.sleep()` / `wait(timeout)` / `tryLock(timeout)` | `TIMED_WAITING` |
| `ReentrantLock.lock()`（内部是 park） | 一般是 `WAITING` |
| 网络 read、磁盘 IO | 常显示 `RUNNABLE` |

本 Demo 的 `blocked-thread` 是标准监视器等待；`lock-holder` 持锁在 `sleep`，它自己是 `TIMED_WAITING`，不是 `BLOCKED`。

---

## 七、案例 1：死锁（jstack 自动检测）

### 现象

`deadlock-thread-1` 和 `deadlock-thread-2` 都是 `BLOCKED`，且各自 **locked 一把锁、waiting to lock 另一把**：

```
"deadlock-thread-1" ...
   java.lang.Thread.State: BLOCKED (on object monitor)
	at ...JstackTroubleDemo.java:36
	- waiting to lock <0x...> (a java.lang.Object)    ← 等 LOCK_B
	- locked <0x...> (a java.lang.Object)             ← 已持有 LOCK_A

"deadlock-thread-2" ...
   java.lang.Thread.State: BLOCKED (on object monitor)
	at ...JstackTroubleDemo.java:46
	- waiting to lock <0x...> (a java.lang.Object)    ← 等 LOCK_A
	- locked <0x...> (a java.lang.Object)             ← 已持有 LOCK_B
```

### jstack 末尾会直接给出结论

```
Found one Java-level deadlock:
=============================
"deadlock-thread-1":
  waiting to lock monitor ... (object 0x...fe13e80, a java.lang.Object),
  which is held by "deadlock-thread-2"

"deadlock-thread-2":
  waiting to lock monitor ... (object 0x...fe13e70, a java.lang.Object),
  which is held by "deadlock-thread-1"

Found 1 deadlock.
```

### 对应源码

```java
// thread-1: 先 A 后 B
synchronized (LOCK_A) {
    Thread.sleep(100);
    synchronized (LOCK_B) { ... }
}

// thread-2: 先 B 后 A  → 经典环路死锁
synchronized (LOCK_B) {
    Thread.sleep(100);
    synchronized (LOCK_A) { ... }
}
```

### 处理思路

- 统一全局加锁顺序（都先 A 后 B）
- 缩小 synchronized 范围
- 用 `tryLock(timeout)` 避免无限等待
- 开发环境可加 `-XX:+PrintConcurrentLocks` 辅助

---

## 八、案例 2：阻塞（等锁，非死锁）

### 现象

```
"lock-holder" ...
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(...)
	at ...JstackTroubleDemo.sleepQuietly(...)
	at ...JstackTroubleDemo.java:56
	- locked <0x...fe13e90> (a java.lang.Object)      ← 占着锁在 sleep

"blocked-thread" ...
   java.lang.Thread.State: BLOCKED (on object monitor)
	at ...JstackTroubleDemo.java:64
	- waiting to lock <0x...fe13e90> (a java.lang.Object)  ← 同一把锁
```

### 读法

- `lock-holder` **持有** `0x...fe13e90`，在 `sleep`，不会释放锁
- `blocked-thread` **等待** 同地址的锁 → 典型「锁竞争阻塞」
- **没有** deadlock 报告 → 不是环路，只是慢/不释放

### 处理思路

- 检查谁 `locked` 了目标锁，栈顶在做什么
- 减少持锁时间；不要在锁内 sleep / RPC / IO
- 考虑读写锁、无锁结构

---

## 九、案例 3：死循环（CPU 高）

### 现象

```
"busy-loop-thread" #24 ... cpu=15105.17ms elapsed=15.64s ... runnable
   java.lang.Thread.State: RUNNABLE
	at io.weli.concurrent.JstackTroubleDemo.lambda$startBusyLoop$4(JstackTroubleDemo.java:75)
```

### 读法

1. **`cpu=15105ms`** 在 15 秒进程里占满 → 该线程在疯狂消耗 CPU
2. 状态 **`RUNNABLE`**，栈顶停在 `while (true)` 行（第 75 行）
3. 多次 jstack 若栈顶 **不变** → 确认忙等，不是正常短任务

### 配合 top 看 CPU（可选）

```bash
top -pid 14422
# 或
ps -M 14422   # macOS 看线程
```

### 处理思路

- 循环内加 `Thread.sleep` / 阻塞队列 / `LockSupport.parkNanos`
- 修复条件退出；用 profiler（async-profiler、JFR）找热点

---

## 十、输出里有哪些线程

`jstack` / `jcmd Thread.print` 打的是**这一刻 JVM 里几乎所有 Java 线程**，不只是业务线程。按线程名分成三类，先过滤再读栈。

### 10.1 `main`

```
"main" ...
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(...)
	at ...JstackTroubleDemo.main(JstackTroubleDemo.java:28)
```

这是 Demo 故意 `Thread.sleep(Long.MAX_VALUE)` 让 JVM 不退出，**不是故障**。生产里 servlet 容器一类进程，`main` 往往早就结束，JVM 靠其它非 daemon 线程活着，输出里可以没有 `"main"`。

### 10.2 业务线程

你 `new Thread(..., "名字")` 或线程池起的，例如本 Demo 的 `deadlock-thread-1`、`blocked-thread`、`busy-loop-thread`，以及现场的 `pool-1-thread-N`、`http-nio-...`。排查时主要看这些。

### 10.3 JVM / JDK 自己的线程

栈顶多半在 `java.lang.*` 或本地帧，没有业务类名，扫一眼即可：

| 线程名 | 大致职责 |
|--------|----------|
| `Reference Handler` / `Finalizer` / `Common-Cleaner` | 引用队列、finalize、cleaner |
| `Signal Dispatcher` | 处理 OS 信号 |
| `C1 CompilerThread` / `C2 CompilerThread` | JIT |
| `GC Thread#` / `G1 Conc#` / `G1 Main Marker` 等 | GC |
| `VM Thread` | VM 内部操作（部分 safepoint 相关） |
| `Service Thread` / `Notification Thread` | 服务、JMX 通知 |
| `Attach Listener` | `jstack` / `jcmd` 自己 attach 时会出现 |

过滤示例：

```bash
# 只看本 Demo 业务线程 + deadlock 结论
grep -E 'deadlock-|blocked-thread|lock-holder|busy-loop|"main"|Found.*deadlock' /tmp/jstack-demo.txt
```

---

## 十一、常见问题

| 问题 | 原因 | 解决 |
|------|------|------|
| `jstack: command not found` | 未装 JDK 或 PATH 不对 | 用 `$JAVA_HOME/bin/jstack` |
| `Unable to open socket file` | 权限不足 | `sudo jstack <pid>` 或同用户运行 |
| 输出里没有业务线程 | 抓错 PID | `jps -l` 再确认 |
| 输出里全是 `GC Thread` / `C2 CompilerThread` | 正常，JVM 内部线程也会打印 | 按业务线程名 `grep`，见第十节 |
| `ReentrantLock` 卡住但不是 `BLOCKED` | `lock()` 走 park，状态是 `WAITING` | 看栈顶是否在 `AbstractQueuedSynchronizer` |
| 没有 deadlock 段但怀疑死锁 | 仅 synchronized 环路能自动检测 | 手动看 `locked` / `waiting to lock` 配对 |

---

## 十二、清理

```bash
# 找到 PID 后
kill <pid>

# 或
jps -l | grep JstackTroubleDemo | awk '{print $1}' | xargs kill
```

---

## 十三、一条龙命令（复制即用）

```bash
cd /path/to/java-snippets
mvn -q compile -DskipTests

# 终端 1：启动 Demo
java -cp target/classes io.weli.concurrent.JstackTroubleDemo

# 终端 2：等 2 秒后抓栈（把 PID 换成终端 1 打印的值）
sleep 2
PID=$(jps -l | grep JstackTroubleDemo | awk '{print $1}')
echo "PID=$PID"
jstack "$PID" | tee /tmp/jstack-demo.txt
grep -A2 'deadlock\|busy-loop\|blocked-thread\|Found.*deadlock' /tmp/jstack-demo.txt
```

---

## 十四、相关文件

- 示例代码：`src/main/java/io/weli/concurrent/JstackTroubleDemo.java`
- 总计划（第 4 日）：`docs/jdk-toolkit-learning-plan.md`
- 等价命令：`jcmd <pid> Thread.print`（`docs/jcmd-troubleshooting-guide.md`）
- 内存排查：`docs/jmap-troubleshooting-guide.md`
