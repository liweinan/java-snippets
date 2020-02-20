package io.alchemystudio.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// https://www.baeldung.com/thread-pool-java-and-guava
public class PlayWithCachedThreadPool {
    private static ExecutorService pool = Executors.newCachedThreadPool();

    @FunctionalInterface
    interface Work {
        void work() throws Exception;
    }

    static class MyTask implements Runnable {
        private Work work;

        public MyTask(Work work) {
            this.work = work;
        }

        @Override
        public void run() {
            try {
                work.work();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            pool.submit(new MyTask(() -> {
                Thread.sleep(1000);
                Map m = new HashMap();
                for (int i = 0; i < 100; i++)
                    m.put(1, 2);
                System.out.println("TASK 1");
            }));
            pool.submit(new MyTask(() -> {
                Map m = new HashMap();
                for (int i = 0; i < 100; i++)
                    m.put(1, 2);
                Thread.sleep(500);
                System.out.println("TASK 2");
            }));
            Thread.sleep(5);
        }
//        pool.shutdown();
    }
}
