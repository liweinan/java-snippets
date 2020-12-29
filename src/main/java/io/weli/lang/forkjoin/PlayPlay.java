package io.weli.lang.forkjoin;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class PlayPlay {
   public static void main(String[] args) throws Exception {
      ForkJoinPool pool = new ForkJoinPool();
      FooTask task = new FooTask();
      ForkJoinTask<String> forkedTask = ForkJoinTask.adapt(task);
      pool.invoke(forkedTask);
      System.out.println("::: " + forkedTask.isDone());
      System.out.println(forkedTask.get());
   }

   public static class FooTask implements Callable<String> {
      public String call() {
         try {
            Thread.sleep(1000);
         } catch (InterruptedException e) {
            System.out.println(e);
         }
         return "Task Done";
      }
   }
}
