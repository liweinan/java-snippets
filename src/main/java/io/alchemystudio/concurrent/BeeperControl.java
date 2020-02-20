package io.alchemystudio.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

class BeeperControl {

   public static void main(String[] args) {
      new BeeperControl().beepForAnHour();
      new BeeperControl().sameRate();
   }

   public void beepForAnHour() {
      final Runnable beeper = () -> System.out.println("beep");
      final ScheduledFuture<?> beeperHandle =
            scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
      scheduler.schedule(new Runnable() {
         public void run() {
            beeperHandle.cancel(true);
         }
      }, 60 * 60, SECONDS);
   }

   private final ScheduledExecutorService scheduler =
         Executors.newScheduledThreadPool(1);


   public void sameRate() {
      final Runnable aaa = new Thread(() -> {
         try {
            Thread.sleep(500);
            System.out.println("AAA");
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      });

      final Runnable bbb = new Thread(() -> {
         try {
            Thread.sleep(500);
            System.out.println("BBB");
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      });

      scheduler.scheduleAtFixedRate(aaa, 1, 1, SECONDS);
      scheduler.scheduleAtFixedRate(bbb, 1, 1, SECONDS);
   }
}
