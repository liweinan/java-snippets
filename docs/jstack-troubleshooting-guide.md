# jstack 线程问题排查实操手册

本手册基于仓库中的 `io.weli.concurrent.JstackTroubleDemo`，**已在本机实际跑通并抓取 jstack 输出**。

---

## 一、你会排查的三类问题

| 类型 | 线程名 | 现象 | jstack 关键特征 |
|------|--------|------|-----------------|
| 死锁 | `deadlock-thread-1/2` | 两个线程互相等锁，永远卡住 | 末尾 `Found one Java-level deadlock` |
| 阻塞 | `blocked-thread` | 等别人持有的锁 | `BLOCKED (on object monitor)` + `waiting to lock` |
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

- 栈不变 + `RUNNABLE` 在同一行 → 可能是死循环
- 栈一直 `BLOCKED` / `WAITING` → 锁或 I/O 阻塞
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
| `RUNNABLE` | 正在运行或等 CPU | 正常计算、忙等死循环、网络 read 也可能显示 RUNNABLE |
| `BLOCKED` | 等 synchronized 锁 | 锁被别的线程占用 |
| `WAITING` | 无限期等待 | `Object.wait()`、`LockSupport.park()`、`join()` |
| `TIMED_WAITING` | 限时等待 | `Thread.sleep()`、`wait(timeout)` |

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

## 十、main 线程为什么也在栈里

```
"main" ...
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(...)
	at ...JstackTroubleDemo.main(JstackTroubleDemo.java:28)
```

这是 Demo 故意 `Thread.sleep(Long.MAX_VALUE)` 让 JVM 不退出，**不是故障**。生产里 main 可能已结束，只剩业务线程。

---

## 十一、常见问题

| 问题 | 原因 | 解决 |
|------|------|------|
| `jstack: command not found` | 未装 JDK 或 PATH 不对 | 用 `$JAVA_HOME/bin/jstack` |
| `Unable to open socket file` | 权限不足 | `sudo jstack <pid>` 或同用户运行 |
| 输出里没有业务线程 | 抓错 PID | `jps -l` 再确认 |
| 没有 deadlock 段但怀疑死锁 | 仅 synchronized 环路能检测 | 手动看 `locked` / `waiting to lock` 配对 |

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
- 本手册：`docs/jstack-troubleshooting-guide.md`
