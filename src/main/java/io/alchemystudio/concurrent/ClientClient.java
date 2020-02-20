package io.alchemystudio.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ClientClient {


   public static void main(String[] args) throws Exception {


      // 主线程要得到异步的Foo的instance
      AsyncClient asyncClient = new AsyncClient();

      Future obj = asyncClient.call();

      // 可以干别的不影响
      System.out.println("主线程在干别的");


      // 得到foo的处理结果，调用`getResult()`的时候为同步。
      System.out.println(obj.get()); // 会block住主线程


   }

   public static class AsyncClient {

      CountDownLatch latch = new CountDownLatch(1);
      private Future val;

      public Future call() {
         System.out.println("启动新线程处理Foo的instance");
         FutureObject obj = new FutureObject();
         new Thread() {
            @Override
            public void run() {
               try {
                  Thread.sleep(1000 * 3);
                  obj.setVal("Hello, world");
                  super.run();
                  latch.countDown();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
         }.start();
         return obj;
      }

//      public String getResult() throws InterruptedException {
//         latch.await();
//         return val;
//      }

      class FutureObject implements Future {

         private String val;

         public void setVal(String val) {
            this.val = val;
         }

         @Override
         public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
         }

         @Override
         public boolean isCancelled() {
            return false;
         }

         @Override
         public boolean isDone() {
            return false;
         }

         @Override
         public Object get() throws InterruptedException, ExecutionException {
            latch.await();
            return val;
         }

         @Override
         public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
         }
      }
   }

}
