package io.weli.concurrent;


import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.locks.ReentrantLock;

public class AutoCleanupCachedThreadPool {

    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private static final List<Task> tasks = Collections.synchronizedList(new ArrayList<>());
    private static final Just<ExecutorService> autoCleanupTask = new Just(Executors.newSingleThreadExecutor());
    private static final ReentrantLock lock = new ReentrantLock();

    public static void cleanup() {
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
        } finally {
            System.out.println("*** CLEAN-UP DONE ***");
        }
    }

    public static synchronized void scheduleAutoCleanup(long milli) {
        lock.lock();
        if (!autoCleanupTask.get().isShutdown()) {
            autoCleanupTask.get().submit(() -> scheduled(milli, () -> cleanup()));
        } else {
            autoCleanupTask.set(Executors.newSingleThreadExecutor());
            autoCleanupTask.get().submit(() -> scheduled(milli, () -> cleanup()));
        }
        lock.unlock();
    }

    public static synchronized void shutdownAutoCleanup() {
        lock.lock();
        autoCleanupTask.get().shutdownNow();
        System.out.println("!!! SHUTDOWN NOW !!!");
        lock.unlock();
    }


    private static void scheduled(long milli, Task.Job job) {
        try {
            while (true) {
                try {
                    Thread.sleep(milli);
                    job.doJob();
                } catch (InterruptedException e) {
                    System.out.println("[][][][][][] 强制退出 [][][][][][]");
                    break;
                }
            }
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
        private long duration;


        public boolean isDone() {
            return job.isDone();
        }

        @FunctionalInterface
        interface Job {
            long start = System.currentTimeMillis();
            Just<Long> duration = new Just(0);

            void doJob() throws Exception;

            Just<Boolean> done = new Just<>(Boolean.FALSE);

            default void markDone() {
                done.set(Boolean.TRUE);
                duration.set(System.currentTimeMillis() - start);
            }

            default boolean isDone() {
                return done.get();
            }

            default long duration() {
                return duration.get();
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
        try {
            pool.submit(task);
        } catch (RejectedExecutionException e) {
            System.out.println("全局容器已经shutdown()");
        }
    }

    // 当前任务数
    public static int taskNumber() {
        return tasks.size();
    }

    public static void stat() {
        System.out.println("::: pool里面的任务数: " + tasks.size());
    }

    public static void main(String[] args) throws Exception {
        System.out.println(":::START:::");


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AutoCleanupCachedThreadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getId() + " 干就完了");
                    System.out.println("{}{}{}{}{}当前任务数： " + AutoCleanupCachedThreadPool.taskNumber());
                    System.out.println(Thread.currentThread().getId() + " 没毛病");
                });
            }
        }).start();



        Thread.sleep(2000);


        AutoCleanupCachedThreadPool.cleanup();

        Thread.sleep(2000);
        AutoCleanupCachedThreadPool.scheduleAutoCleanup(1000);
        Thread.sleep(5000);
        AutoCleanupCachedThreadPool.shutdownAutoCleanup();
        Thread.sleep(3000);
        AutoCleanupCachedThreadPool.scheduleAutoCleanup(1000);


    }

}
