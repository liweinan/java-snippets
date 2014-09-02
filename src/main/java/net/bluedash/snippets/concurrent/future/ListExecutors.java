package net.bluedash.snippets.concurrent.future;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ListExecutors {
    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(3);
        System.out.println("Executors.newFixedThreadPool() " + executor.getClass());

        executor = Executors.newCachedThreadPool();
        System.out.println("Executors.newCachedThreadPool() " + executor.getClass());

        executor = Executors.newSingleThreadExecutor();
        System.out.println("Executors.newSingleThreadExecutor() " + executor.getClass());

        executor = Executors.newScheduledThreadPool(3);
        System.out.println("Executors.newScheduledThreadPool() " + executor.getClass());

        Executor myExecutor = new WithinThreadExecutor();
        myExecutor.execute(new DummyRunnable()); // When an object implementing interface Runnable is used to create a thread,
        // starting the thread causes the object's run method to be called in that separately executing thread.
    }
}
