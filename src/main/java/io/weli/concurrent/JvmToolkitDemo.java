package io.weli.concurrent;

/**
 * 轻量常驻进程，供 jps / jstat / jinfo / jcmd / jconsole / jhsdb 练习。
 * 后台线程持续分配短生命周期对象，触发 Young GC（不持有引用，不是泄漏）。
 *
 * 启动建议：
 *   java -Xms64m -Xmx128m -XX:+UseG1GC \
 *     -Dtoolkit.demo.name=JvmToolkitDemo \
 *     -cp target/classes io.weli.concurrent.JvmToolkitDemo
 *
 * 运行后另开终端：
 *   jps -lvm
 *   jstat -gcutil <pid> 1000 10
 *   jinfo -sysprops <pid>
 *   jcmd <pid> help
 */
public class JvmToolkitDemo {

    private static final int YOUNG_CHUNK_BYTES = 64 * 1024;
    private static final int YOUNG_ALLOCS_PER_ROUND = 32;

    /** 防止 JIT 消除分配 */
    private static volatile byte allocationSink;

    public static void main(String[] args) throws Exception {
        long pid = ProcessHandle.current().pid();
        System.out.println("JvmToolkitDemo started, PID = " + pid);
        System.out.println("  toolkit.demo.name = " + System.getProperty("toolkit.demo.name", "(unset)"));
        System.out.println("Run: jps -lvm");
        System.out.println("Run: jstat -gcutil " + pid + " 1000 10");
        System.out.println("Run: jinfo -sysprops " + pid);
        System.out.println("Run: jcmd " + pid + " help");

        Thread allocator = new Thread(JvmToolkitDemo::allocateYoungGarbage, "young-allocator");
        allocator.setDaemon(true);
        allocator.start();

        Thread.sleep(Long.MAX_VALUE);
    }

    private static void allocateYoungGarbage() {
        long round = 0;
        while (true) {
            for (int i = 0; i < YOUNG_ALLOCS_PER_ROUND; i++) {
                byte[] chunk = new byte[YOUNG_CHUNK_BYTES];
                allocationSink = chunk[0];
            }
            round++;
            if (round % 50 == 0) {
                System.out.printf("young-alloc round %d%n", round);
            }
            sleepQuietly(50);
        }
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
