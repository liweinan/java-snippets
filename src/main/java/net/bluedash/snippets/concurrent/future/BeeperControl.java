package net.bluedash.snippets.concurrent.future;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class BeeperControl {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void beepForAnHour() {
        final Runnable beeper = new Runnable() {
            public void run() {
                System.out.println("beep");
            }
        };

        final ScheduledFuture<?> beeperHandler = scheduler.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS); // beep every 10 seconds

        scheduler.schedule(new Runnable() {
            public void run() {
                beeperHandler.cancel(true);
            }
        }, 60, TimeUnit.MINUTES); // stop after one hour passed

        scheduler.schedule(new Runnable() {
            public void run() {
                System.out.println("hahaha! 15 seconds passed!");
            }
        }, 15, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        System.out.println(TimeUnit.SECONDS.getClass());

        System.out.println("TimeUnit.SECONDS.toDays(1) -> " + TimeUnit.SECONDS.toDays(1));

        beepForAnHour();
    }

}
