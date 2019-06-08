package lang;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 * https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/completion-stage-and-completable-future.html
 */
public class SupplyExample2 {
   public static void main(String[] args) {
      CompletableFuture.supplyAsync(() -> ThreadLocalRandom.current().nextInt(1, 10))
              .thenApply(v -> {
                 System.out.println("thread-id: " + Thread.currentThread().getId());
                 return Math.sqrt(v);
              })
              .thenAccept(System.out::println)
              .join();
   }
}
