package io.weli.concurrent;

/**
 * 故意制造三类线程问题，供 jstack 实操排查：
 * 1. 死锁（deadlock）
 * 2. 阻塞（blocked，等锁）
 * 3. 忙等死循环（Runnable，CPU 飙高）
 *
 * 运行后另开终端：jps -l | grep JstackTroubleDemo
 *                  jstack <pid>
 */
public class JstackTroubleDemo {

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();
    private static final Object HELD_LOCK = new Object();

    public static void main(String[] args) throws Exception {
        long pid = ProcessHandle.current().pid();
        System.out.println("JstackTroubleDemo started, PID = " + pid);
        System.out.println("Run: jstack " + pid);

        startDeadlock();
        startBlocking();
        startBusyLoop();

        // 主线程不退出，方便反复 jstack
        Thread.sleep(Long.MAX_VALUE);
    }

    private static void startDeadlock() {
        Thread t1 = new Thread(() -> {
            synchronized (LOCK_A) {
                sleepQuietly(100);
                synchronized (LOCK_B) {
                    System.out.println("deadlock-t1 acquired both locks");
                }
            }
        }, "deadlock-thread-1");
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (LOCK_B) {
                sleepQuietly(100);
                synchronized (LOCK_A) {
                    System.out.println("deadlock-thread-2 acquired both locks");
                }
            }
        }, "deadlock-thread-2");
        t2.start();
    }

    private static void startBlocking() {
        Thread holder = new Thread(() -> {
            synchronized (HELD_LOCK) {
                sleepQuietly(Long.MAX_VALUE);
            }
        }, "lock-holder");
        holder.start();

        Thread waiter = new Thread(() -> {
            sleepQuietly(200);
            synchronized (HELD_LOCK) {
                System.out.println("blocked-thread got lock");
            }
        }, "blocked-thread");
        waiter.start();
    }

    private static void startBusyLoop() {
        Thread busy = new Thread(() -> {
            long counter = 0;
            while (true) {
                counter++;
                if (counter == Long.MAX_VALUE) {
                    counter = 0;
                }
            }
        }, "busy-loop-thread");
        busy.start();
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
