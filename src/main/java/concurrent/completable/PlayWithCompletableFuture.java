package concurrent.completable;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class PlayWithCompletableFuture {

    public static void main(String[] args) throws Exception {

        {
            // 最简单的使用方法
            CompletableFuture<String> future0 = new CompletableFuture<>();

            new Thread(() -> {
                try {
                    sleep(1000);
                    future0.complete("Finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            System.out.println(future0.get()); // 会block在这里，直到future0.complete()

        }

        {
            // completable future 可以放到executor service里面去执行
            // 这个时候起到了类似锁的作用
            ExecutorService executor = Executors.newSingleThreadExecutor();

            Future future1 = executor.submit(() -> {
                sleep(50);
                System.out.println("Hello from worker!");
                return "this is result";
            });

            System.out.println(future1.get());

            CompletableFuture<String> future2 = new CompletableFuture<>();

            executor.submit(() -> {
                future2.complete("Computation done");
                return;
            });

            System.out.println(future2.get());

        }

        {
            // 这个Supplier接口感觉和Future接口挺像的。
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                return "42";
            });

            System.out.println(future.get());
        }

    }
}
