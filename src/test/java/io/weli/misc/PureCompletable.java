package io.weli.misc;

import org.junit.jupiter.api.Assertions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PureCompletable {
    public static void main(String[] args) throws Exception {
        {
            Future<String> completableFuture =
                    CompletableFuture.completedFuture("Hello");

            String result = completableFuture.get();
            Assertions.assertEquals("Hello", result);
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

        {

            var c = CompletableFuture.supplyAsync(() -> {
                throw new RuntimeException("boom");
            }).handle((o, e) -> {
                System.out.println(e);
                return o;
            });

            System.out.println(c.get());
        }
    }
}
