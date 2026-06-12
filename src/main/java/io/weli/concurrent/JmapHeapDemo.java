package io.weli.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * 故意在堆上持续分配并持有对象，供 jmap 实操排查内存问题。
 *
 * 运行后另开终端：
 *   jps -l | grep JmapHeapDemo
 *   jmap -histo <pid>
 *   jmap -histo:live <pid>
 *   jmap -heap <pid>
 *   jmap -dump:format=b,file=/tmp/heap.hprof <pid>
 */
public class JmapHeapDemo {

    /** 模拟泄漏：只增不减 */
    private static final List<byte[]> LEAKED_BUFFERS = new ArrayList<>();
    private static final List<String> STRING_LEAK = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        long pid = ProcessHandle.current().pid();
        System.out.println("JmapHeapDemo started, PID = " + pid);
        System.out.println("Run: jmap -histo " + pid);
        System.out.println("Run: jmap -heap " + pid);
        System.out.println("Run: jmap -dump:format=b,file=/tmp/heap.hprof " + pid);

        Thread leaker = new Thread(JmapHeapDemo::leakLoop, "heap-leaker");
        leaker.start();

        Thread.sleep(Long.MAX_VALUE);
    }

    private static void leakLoop() {
        int round = 0;
        while (true) {
            LEAKED_BUFFERS.add(new byte[512 * 1024]); // 512 KB
            for (int i = 0; i < 1_000; i++) {
                STRING_LEAK.add("leak-round-" + round + "-item-" + i);
            }
            round++;
            System.out.printf("leak round %d, buffers=%d, strings=%d%n",
                    round, LEAKED_BUFFERS.size(), STRING_LEAK.size());
            sleepQuietly(500);
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
