package io.alchemystudio.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class AutoCleanupCachedThreadPool {

    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private static final List<Task> tasksInProgress = Collections.synchronizedList(new ArrayList());
    private static final Just<ExecutorService> autoCleanupTask = new Just(Executors.newSingleThreadExecutor());
    private static final ReentrantLock lock = new ReentrantLock();
    private static final AtomicLong taskTimeoutInterval = new AtomicLong(Long.MAX_VALUE);
    private static final AtomicLong autoCleanupInterval = new AtomicLong(Long.MAX_VALUE);
    private static final Random random = new Random(System.currentTimeMillis());

    private static final List<Task> timeoutTasks = Collections.synchronizedList(new ArrayList());

    private static final AtomicBoolean enableTimeoutTasksReservation = new AtomicBoolean(Boolean.FALSE);

    public static void enableTimeoutTasksReservation() {
        enableTimeoutTasksReservation.set(Boolean.TRUE);
    }

    public static void disableTimeoutTasksReservation() {
        enableTimeoutTasksReservation.set(Boolean.FALSE);
        timeoutTasks.clear();
    }

    public static boolean timeoutTasksReservationIsEnabled() {
        return enableTimeoutTasksReservation.get();
    }

    public static List<Task> popAllTimeoutTasks() {
        List<Task> out = new ArrayList(timeoutTasks);
        timeoutTasks.clear();
        for (Task t : out) {
            t.markInProgress();
        }
        return Collections.unmodifiableList(out);
    }

    private static void setTaskTimeoutInterval(long milli) {
        taskTimeoutInterval.set(milli);
    }

    public static void setAutoCleanupInterval(long milli) {
        autoCleanupInterval.set(milli);
    }

    // 清理timeout task
    static {
        Executors.newSingleThreadExecutor().submit(() -> {
            scheduled(1000, () -> {
                try {
                    for (Task t : tasksInProgress) {
                        System.out.println("任务： " + t + " 时长：" + t.getDuration());
                        if (t.getDuration() > taskTimeoutInterval.get()) {
                            System.out.println("任务超时： " + t + " 时长：" + t.getDuration());
                            t.getFuture().cancel(true);
                            t.markDone();
                            if (timeoutTasksReservationIsEnabled()) {
                                addUntilSuccess(timeoutTasks, t);
                            }
                        }
                    }
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private static void addUntilSuccess(List<Task> l, Task t) {
        try {
            l.add(t);
        } catch (ConcurrentModificationException e) {
            addUntilSuccess(l, t);
        }
    }

    public static void cleanupCompletedTasks() {
        List<Task> toBeDeleted = new ArrayList<>();
        try {
            for (Task task : tasksInProgress) {
                if (task.isDone()) {
                    System.out.println("清除已经完成的任务：" + task);
                    toBeDeleted.add(task);
                }
            }
            removeAllUntilSuccess(tasksInProgress, toBeDeleted);
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("本批次任务清除完成");
        }
    }

    private static void removeAllUntilSuccess(List<Task> tasksInProgress, List<Task> toBeDeleted) {
        try {
            tasksInProgress.removeAll(toBeDeleted);
        } catch (ConcurrentModificationException e) {
            removeAllUntilSuccess(tasksInProgress, toBeDeleted);
        }
    }

    public static void enableAutoCleanupCompletedTasks() {
        lock.lock();
        if (autoCleanupTask.get().isShutdown()) {
            autoCleanupTask.set(Executors.newSingleThreadExecutor());
        }
        autoCleanupTask.get().submit(() -> scheduled(autoCleanupInterval.get(), AutoCleanupCachedThreadPool::cleanupCompletedTasks));
        lock.unlock();
    }

    public static void disableAutoCleanupCompletedTasks() {
        lock.lock();
        autoCleanupTask.get().shutdownNow();
        System.out.println("关停已完成任务自动清理");
        lock.unlock();
    }


    private static void scheduled(long milli, Task.Job job) {
        try {
            while (true) {
                try {
                    Thread.sleep(milli + random.nextInt(100));
                    job.doJob();
                } catch (InterruptedException e) {
                    System.out.println("定时任务强制退出");
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

        long start = System.currentTimeMillis();

        private volatile boolean done = false;

        public void markDone() {
            done = true;
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

        public void markInProgress() {
            done = false;
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
                System.out.println("任务强制退出：" + this);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                markDone();
            }
        }
    }

    public static void submit(Task.Job job) {
        Task task = new Task(job);
        task.markInProgress();
        System.out.println("提交任务：" + task);
        tasksInProgress.add(task);
        try {
            Future f = pool.submit(task);
            task.setFuture(f);
        } catch (RejectedExecutionException e) {
            System.out.println("任务提交失败：全局容器已经shutdown");
        }
    }

    // 当前任务数
    public static int taskNumber() {
        return tasksInProgress.size();
    }

    public static void stat() {
        System.out.println("::: pool里面的任务数: " + tasksInProgress.size());
    }

    public static void main(String[] args) throws Exception {
        {
            System.out.println(":::测试一:::");
            System.out.println(":::设置任务超时时间:::");
            AutoCleanupCachedThreadPool.setTaskTimeoutInterval(1000);
            System.out.println(":::设置自动清理完成任务时间间隔:::");
            AutoCleanupCachedThreadPool.setAutoCleanupInterval(1000);
            AutoCleanupCachedThreadPool.enableAutoCleanupCompletedTasks();
            System.out.println("::: 提交死循环任务 :::");
            AutoCleanupCachedThreadPool.submit(() -> {
                while (true) {
                    System.out.println("{死循环任务}");
                    Thread.sleep(5000);
                }
            });

            System.out.println("::: 提交超时任务 :::");
            AutoCleanupCachedThreadPool.submit(() -> {
                Thread.sleep(10000);
                System.out.println("{超时任务}");
            });

            System.out.println("::: 提交正常任务 :::");
            AutoCleanupCachedThreadPool.submit(() -> {
                Thread.sleep(400);
                System.out.println("{正常任务}");
            });
            System.out.println("::: 等待5秒钟，等自动任务清理完成 :::");
            Thread.sleep(5000);
            System.out.println(":::关闭已完成任务自动清除:::");
            AutoCleanupCachedThreadPool.disableAutoCleanupCompletedTasks();
            System.out.println("::: 再次提交一个任务 :::");
            AutoCleanupCachedThreadPool.submit(() -> {
                Thread.sleep(400);
                System.out.println("{正常任务}");
            });
            System.out.println("::: 等待任务完成 :::");
            Thread.sleep(2000);
            System.out.println(":::手工清除已经处理完的任务:::");
            AutoCleanupCachedThreadPool.cleanupCompletedTasks();
            System.out.println(":::测试完成:::");
            Thread.sleep(2000);
        }

        {
            System.out.println(":::测试二:::");
//            System.out.println(":::重新启动已完成任务自动清除:::");
//            AutoCleanupCachedThreadPool.enableAutoCleanupCompletedTasks();
            Thread.sleep(2000);
            System.out.println(":::打开超时任务存储:::");
            AutoCleanupCachedThreadPool.enableTimeoutTasksReservation();
            System.out.println("::: 提交超时任务 :::");
            AutoCleanupCachedThreadPool.submit(() -> {
                Thread.sleep(10000);
                System.out.println("{超时任务}");
            });
            Thread.sleep(2000);
            System.out.println("::: 取得超时任务队列 :::");
            System.out.println("超时任务： " + AutoCleanupCachedThreadPool.popAllTimeoutTasks());
            Thread.sleep(2000);
            System.out.println("::: 手工清理超时任务 :::");
            AutoCleanupCachedThreadPool.cleanupCompletedTasks();
            Thread.sleep(2000);

            // 提交随机超时任务2
            submit2(() -> {
                int duration = random.nextInt(2000);
                System.out.println("{随机超时任务2，本次时长：" + duration + " }");
                Thread.sleep(duration);
                System.out.println("{随机超时任务2执行完成}");

            });

            while (true) {
                System.out.println("::: 取得超时任务队列 :::");
                System.out.println("::: 等待随机超时任务2提交并运行完毕 :::");
                Thread.sleep(4000);
                List<Task> tasks = AutoCleanupCachedThreadPool.popAllTimeoutTasks();
                if (tasks.size() > 0) {
                    System.out.println("::: 取得超时任务2，提交回pool :::");
                    submit2(tasks.get(0).job);
                    break;
                } else {
                    System.out.println("::: 任务2并未超时，重新提交 :::");
                    submit2(() -> {
                        int duration = random.nextInt(2000);
                        System.out.println("{随机超时任务2，本次时长：" + duration + " }");
                        Thread.sleep(duration);
                        System.out.println("{随机超时任务2执行完成}");
                    });
                }
            }

            System.out.println("::: 等待重新提交的任务2运行完毕或者超时 :::");
            Thread.sleep(2000);
            System.out.println("::: 手工清理超时任务 :::");
            AutoCleanupCachedThreadPool.cleanupCompletedTasks();
            Thread.sleep(2000);
            System.out.println("::: 关停超时任务保存 :::");
            AutoCleanupCachedThreadPool.disableTimeoutTasksReservation();
            Thread.sleep(2000);
            System.out.println(":::测试完成:::");
            Thread.sleep(2000);
        }
    }

    private static void submit2(Task.Job t) {
        System.out.println("::: 提交随机超时任务2 :::");
        AutoCleanupCachedThreadPool.submit(t);
    }


}
