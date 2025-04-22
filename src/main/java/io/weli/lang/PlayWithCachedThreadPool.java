package io.weli.lang;

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
        ExecutorService executor = Executors.newCachedThreadPool();
        Map<String, Integer> results = new HashMap<>();
        
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                results.put("Task " + taskId, taskId * 2);
                System.out.println("Task " + taskId + " completed");
            });
        }
        
        executor.shutdown();
        System.out.println("Results: " + results);
    }
}
