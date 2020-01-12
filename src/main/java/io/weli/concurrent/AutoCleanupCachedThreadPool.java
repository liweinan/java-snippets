package io.weli.concurrent;


import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AutoCleanupCachedThreadPool {

    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private static final List<Task> tasks = Collections.synchronizedList(new ArrayList<>());

    // 定时清除已经完成的任务
    static {
        new Thread(() -> {
            while (true) {
                scheduled(1000, () -> {
                    List<Task> toBeDeleted = new ArrayList<>();
                    try {
                        for (Task task : tasks) {
                            if (task.isDone()) {
                                // todo: 把System.out改成logger
                                System.out.println("<><><><><>清除已经完成的任务：" + task);
                                toBeDeleted.add(task);
                            }
                        }
                        tasks.removeAll(toBeDeleted);
                    } catch (ConcurrentModificationException e) {
                        // ignore
                    }
                });
            }
        }).start();
    }

    private static void scheduled(int milli, Task.Job job) {
        try {
            Thread.sleep(milli);
            job.doJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static class Just<T> {
        private T obj;

        public Just(T obj) {
            this.obj = obj;
        }

        public T get() {
            return obj;
        }

        public void set(T obj) {
            this.obj = obj;
        }
    }


    static class Task implements Runnable {

        public boolean isDone() {
            return job.isDone();
        }

        @FunctionalInterface
        interface Job {
            void doJob() throws Exception;

            Just<Boolean> done = new Just<>(Boolean.FALSE);

            default void markDone() {
                done.set(Boolean.TRUE);
            }

            default boolean isDone() {
                return done.get();
            }
        }

        private Job job;

        public Task(Job job) {
            this.job = job;
        }

        @Override
        public void run() {
            try {
                job.doJob();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                job.markDone();
            }
        }
    }

    public static void submit(Task.Job job) {
        Task task = new Task(job);
        tasks.add(task);
        pool.submit(task);
    }

    public static void stat() {
        System.out.println("::: pool里面的任务数: " + tasks.size());
    }

    public static void main(String[] args) throws Exception {
        System.out.println(":::START:::");

        while (true) {
            Thread.sleep(10);
            AutoCleanupCachedThreadPool.submit(() -> {
                System.out.println(Thread.currentThread().getId() + " 干就完了");
                AutoCleanupCachedThreadPool.stat();
                System.out.println(Thread.currentThread().getId() + " 没毛病");
            });
        }
    }
}
