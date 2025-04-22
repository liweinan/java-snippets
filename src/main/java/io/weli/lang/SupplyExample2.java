package io.weli.lang;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/completion-stage-and-completable-future.html
 */
@SuppressWarnings("deprecation")
public class SupplyExample2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Supplier<String> supplier = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Hello from " + Thread.currentThread().getId();
        };

        CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier);
        System.out.println(future.get());
    }
}
