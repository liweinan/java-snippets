package io.weli.lang;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureThreadDemo {
   public static void main(String[] args) {
      CompletableFuture<Void> cf =
              CompletableFuture.runAsync(() -> {
                 System.out.println("1. running, in thread: " + Thread.currentThread().getName());
              })
                      .thenRunAsync(() -> System.out.println("2. running, in thread: " + Thread.currentThread().getName()))
                      .thenRunAsync(() -> System.out.println("3. running, in thread: " + Thread.currentThread().getName()));

      cf.join(); //waits until task is completed
      System.out.println("main exiting, thread: " + Thread.currentThread().getName());
   }
}
