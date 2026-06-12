package io.weli.concurrent;

import java.util.concurrent.*;

public class FooConn {
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            System.out.println("Hello!");
        }).start();

        Callable<Object> callable = new Callable<>() {
            @Override
            public Object call() throws Exception {
                return "Callable";

            }
        };

        // ------------------------

        FutureTask<Object> futureTask = new FutureTask<>(callable);

        new Thread(futureTask).start();

        System.out.println(futureTask.get());

        // ------------------------
        ExecutorService pool = Executors.newSingleThreadExecutor();

        pool.execute(futureTask);
        System.out.println(futureTask.get());

        // ------------------------


    }
}
