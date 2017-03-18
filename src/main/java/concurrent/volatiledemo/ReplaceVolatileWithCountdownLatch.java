package concurrent.volatiledemo;

import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ReplaceVolatileWithCountdownLatch {
    CountDownLatch wait = new CountDownLatch(1);
    CountDownLatch threadLock = new CountDownLatch(1);

    public void test() {
        new Thread(new Runnable() {
            public void run() {
                threadLock.countDown();
                try {
                    wait.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1 finished.");
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    threadLock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2 shutdown...");
                wait.countDown();
            }
        }).start();
    }

    public static void main(String[] args) {
        new ReplaceVolatileWithCountdownLatch().test();
    }
}
