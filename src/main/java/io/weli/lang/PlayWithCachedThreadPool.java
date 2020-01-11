package io.weli.lang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// https://www.baeldung.com/thread-pool-java-and-guava
public class PlayWithCachedThreadPool {
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        pool.submit(new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("TASK 1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        pool.submit(new Thread(() -> {
            try {
                Thread.sleep(500);
                System.out.println("TASK 2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        pool.shutdown();
    }
}
