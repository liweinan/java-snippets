package io.weli.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

class BeeperControl {
   private final ScheduledExecutorService scheduler =
         Executors.newScheduledThreadPool(1);

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

   public static void main(String[] args) {
      new BeeperControl().beepForAnHour();
   }
}
