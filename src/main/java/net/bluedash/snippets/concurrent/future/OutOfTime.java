package net.bluedash.snippets.concurrent.future;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class OutOfTime {
    public static void main(String[] args) throws Exception {
        // A Timer creates only a single thread for executing timer tasks.
        // The Timer thread doesn't catch the exception, so an unchecked exception thrown from a TimerTask terminates the timer thread.
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        TimeUnit.SECONDS.sleep(1);
        timer.schedule(new ThrowTask(), 1); // Exception in thread "main" java.lang.IllegalStateException: Timer already cancelled.
        TimeUnit.SECONDS.sleep(5);
        // ScheduledThreadPoolExecutor deals properly with ill-behaved tasks; there is little reason to use Timer in Java 5.0 or later
    }


    static class ThrowTask extends TimerTask {
        public void run() {
            throw new RuntimeException();
        }
    }
}
