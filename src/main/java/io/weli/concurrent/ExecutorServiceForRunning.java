package io.weli.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceForRunning {
    public static void main(String[] args) throws Exception {
        ExecutorService s = Executors.newSingleThreadExecutor();
        Future f = s.submit(() -> {
            while (true) {
                Thread.sleep(1000);
                System.out.print(".");
            }
        });

        Thread.sleep(5000);
        f.cancel(true);
        s.shutdown();
    }
}
