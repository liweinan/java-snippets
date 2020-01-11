package io.weli.lang;

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

    public static void main(String[] args) {
        pool.submit(new MyTask(() -> {
            Thread.sleep(1000);
            System.out.println("TASK 1");
        }));
        pool.submit(new MyTask(() -> {
            Thread.sleep(500);
            System.out.println("TASK 2");
        }));


        pool.shutdown();
    }
}
