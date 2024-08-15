package io.weli.lang;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


//import static org.jgroups.util.Util.assertEquals;
//import static org.jgroups.util.Util.assertTrue;

/**
 * https://www.baeldung.com/java-completablefuture
 */
public class PlayAsync {

   public static void main(String[] args) throws Exception {

      {
         CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

         CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");

         assertEquals("Hello World", future.get());

      }

      {
         CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> "Hello");

         CompletableFuture<Void> future2 = completableFuture2.thenAccept(s -> System.out.println("Computation returned: " + s));

         future2.get();
      }

      {
         CompletableFuture<String> completableFuture
                 = CompletableFuture.supplyAsync(() -> "Hello");

         CompletableFuture<Void> future = completableFuture
                 .thenRun(() -> System.out.println("Computation finished."));

         future.get();
      }

      {
         CompletableFuture<String> completableFuture
                 = CompletableFuture.supplyAsync(() -> "Hello")
                 .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));

         assertEquals("Hello World", completableFuture.get());
      }

      {
         CompletableFuture future = CompletableFuture.supplyAsync(() -> "Hello")
                 .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"),
                         (s1, s2) -> System.out.println(s1 + s2));
         future.get();
      }

      {
         CompletableFuture<String> future1
                 = CompletableFuture.supplyAsync(() -> "Hello");
         CompletableFuture<String> future2
                 = CompletableFuture.supplyAsync(() -> "Beautiful");
         CompletableFuture<String> future3
                 = CompletableFuture.supplyAsync(() -> "World");

         CompletableFuture<Void> combinedFuture
                 = CompletableFuture.allOf(future1, future2, future3);

// ...

         combinedFuture.get(); // join

         System.out.println(future1.get());
         System.out.println(future2.get());
         System.out.println(future3.get());

         assertTrue(future1.isDone());
         assertTrue(future2.isDone());
         assertTrue(future3.isDone());

         String combined = Stream.of(future1, future2, future3)
                 .map(CompletableFuture::join)
                 .collect(Collectors.joining(" "));

         assertEquals("Hello Beautiful World", combined);


      }

      {
         String name = null;
// ...

         CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> name);

         completableFuture.handle((s, t) -> s != null ? s : "Hello, Stranger!");

         assertEquals("Hello, Stranger!", completableFuture.get());
      }

      {
         CompletableFuture<String> completableFuture
                 = CompletableFuture.supplyAsync(() -> "Hello");

         CompletableFuture<String> future = completableFuture
                 .thenApplyAsync(s -> s + " World");

         assertEquals("Hello World", future.get());
      }
   }
}
