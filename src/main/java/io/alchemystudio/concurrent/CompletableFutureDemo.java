package io.alchemystudio.concurrent;

import java.util.concurrent.CompletableFuture;

import static org.jgroups.util.Util.assertEquals;

public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });

        CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");

        assertEquals("Hello World", future.get());
    }
}
