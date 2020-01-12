package io.weli.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class AutoCleanupCachedThreadPool {

    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private static final List<Task> tasks = Collections.synchronizedList(new ArrayList<>());
    private static final Just<ExecutorService> autoCleanupTask = new Just(Executors.newSingleThreadExecutor());
    private static final ReentrantLock lock = new ReentrantLock();
    private static final AtomicLong retries = new AtomicLong(Long.MIN_VALUE);
    private static final AtomicLong timeout = new AtomicLong(Long.MAX_VALUE);
    private static final Random random = new Random(System.currentTimeMillis());

    private static void setTaskTimeout(long milli) {
        timeout.set(milli);
    }

    // 清理timeout task
    static {
        Executors.newSingleThreadExecutor().submit(() -> {
            scheduled(1000, () -> {
                try {
                    for (Task t : tasks) {
                        System.out.print("+");
                        System.out.print("{ " + t.getDuration() + " }");
                        if (t.getDuration() > timeout.get()) {
                            System.out.print("{ CANCEL }");
                            t.getFuture().cancel(true);
                            t.markDone();
                            // TODO : 添加retries的支持
                        }
                    }
                } catch (ConcurrentModificationException e) {
                    System.out.print("::: IGNORED CLEAN-UP TASK ConcurrentModificationException :::");
                }
            });
        });
    }

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

    public static void scheduleAutoCleanup(long milli) {
        lock.lock();
        if (autoCleanupTask.get().isShutdown()) {
            autoCleanupTask.set(Executors.newSingleThreadExecutor());
        }
        autoCleanupTask.get().submit(() -> scheduled(milli, AutoCleanupCachedThreadPool::cleanup));
        lock.unlock();
    }

    public static void shutdownAutoCleanup() {
        lock.lock();
        autoCleanupTask.get().shutdownNow();
        System.out.println("!!! SHUTDOWN NOW !!!");
        lock.unlock();
    }


    private static void scheduled(long milli, Task.Job job) {
        try {
            while (true) {
                try {
                    Thread.sleep(milli + random.nextInt(100));
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
        private Future future;
        private Job job;
        private volatile int retriedTimes;

        long start = System.currentTimeMillis();

        private volatile boolean done = false;

        public void markDone() {
            done = true;
        }

        public void increaseRetriedTimes() {
            retriedTimes++;
        }

        // TODO: 支持retry.
        public int getRetriedTimes() {
            return retriedTimes;
        }

        public Task(Job job) {
            this.job = job;
        }

        public boolean isDone() {
            return done;
        }

        public void setFuture(Future future) {
            this.future = future;
        }

        public Future getFuture() {
            return future;
        }

        public long getDuration() {
            return System.currentTimeMillis() - start;
        }

        @FunctionalInterface
        interface Job {
            void doJob() throws Exception;
        }

        @Override
        public void run() {
            try {
                job.doJob();
            } catch (InterruptedException e) {
                System.out.println("<><><><> INTERRUPT <><><><>");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                markDone();
            }
        }
    }

    public static void submit(Task.Job job) {
        Task task = new Task(job);
        System.out.print("[[[ " + task + " ]]]");
        tasks.add(task);
        try {
            Future f = pool.submit(task);
            task.setFuture(f);
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

        AutoCleanupCachedThreadPool.setTaskTimeout(1000);
        AutoCleanupCachedThreadPool.scheduleAutoCleanup(1000);

        AutoCleanupCachedThreadPool.submit(() -> {
            while (true) {
                System.out.print("{{{死循环任务}}}");
                Thread.sleep(5000);
            }
        });

        AutoCleanupCachedThreadPool.submit(() -> {
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getId() + " 干就完了");
            System.out.println("{}{}{}{}{}当前任务数： " + AutoCleanupCachedThreadPool.taskNumber());
            System.out.println(Thread.currentThread().getId() + " 没毛病");
        });

        AutoCleanupCachedThreadPool.submit(() -> {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getId() + " 干就完了");
            System.out.println("{}{}{}{}{}当前任务数： " + AutoCleanupCachedThreadPool.taskNumber());
            System.out.println(Thread.currentThread().getId() + " 没毛病");
        });


        Thread.sleep(2000);
        AutoCleanupCachedThreadPool.cleanup();
        Thread.sleep(2000);
        AutoCleanupCachedThreadPool.shutdownAutoCleanup();
        Thread.sleep(2000);
        AutoCleanupCachedThreadPool.scheduleAutoCleanup(1000);
        Thread.sleep(2000);
        AutoCleanupCachedThreadPool.shutdownAutoCleanup();
    }


}
