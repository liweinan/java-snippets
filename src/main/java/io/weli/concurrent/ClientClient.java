package io.weli.concurrent;

import java.util.concurrent.CountDownLatch;

public class ClientClient {

   public static class AsyncClient {
      CountDownLatch latch = new CountDownLatch(1);

      private String val;

      public String getResult() throws InterruptedException {
         latch.await();
         return val;
      }

      public void process(String _val) {
         System.out.println("启动新线程处理Foo的instance");
         new Thread() {
            @Override
            public void run() {
               try {
                  Thread.sleep(1000 * 3);
                  val = _val;
                  super.run();
                  latch.countDown();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
         }.start();
      }
   }



   public static void main(String[] args) throws Exception {


      // 主线程要得到异步的Foo的instance
      AsyncClient asyncClient = new AsyncClient();

      asyncClient.process("Hello, world!");

      // 可以干别的不影响
      System.out.println("主线程在干别的");


      // 得到foo的处理结果，调用`getResult()`的时候为同步。
      System.out.println(asyncClient.getResult()); // 会block住主线程


   }

}
