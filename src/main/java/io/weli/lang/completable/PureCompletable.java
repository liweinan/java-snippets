package io.weli.lang.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class PureCompletable {
    public static void main(String[] args) throws Exception {
        {
            Future<String> completableFuture =
                    CompletableFuture.completedFuture("Hello");

            String result = completableFuture.get();
            assertEquals("Hello", result);
        }

        {
            var f = CompletableFuture
                    .supplyAsync(() -> {
                        System.out.println("1");
                        return "Hello";
                    })
                    .thenApplyAsync(s -> {
                        System.out.println("2");
                        return s.toUpperCase();
                    }).thenAcceptAsync(System.out::println);

            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-");
            f.get(1, TimeUnit.SECONDS);

        }
    }
}
