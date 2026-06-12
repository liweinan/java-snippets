package io.weli.concurrent;

import java.util.concurrent.*;

public class RejectDemo {
    public static void main(String[] args) {
        // 创建核心线程数=1，最大线程数=1，队列容量=1
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,                          // corePoolSize
                1,                          // maximumPoolSize
                0L,                         // keepAliveTime
                TimeUnit.MILLISECONDS,      // unit
                new ArrayBlockingQueue<>(1), // workQueue（容量1）
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
        );

        // 提交3个任务，触发拒绝
        for (int i = 1; i <= 3; i++) {
            final int taskId = i;
            try {
                pool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 执行任务 " + taskId);
                    try {
                        Thread.sleep(1000); // 模拟任务执行
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                System.out.println("任务 " + taskId + " 已提交");
            } catch (RejectedExecutionException e) {
                System.out.println("任务 " + taskId + " 被拒绝: " + e.getMessage());
            }
        }

        pool.shutdown();
    }

}
