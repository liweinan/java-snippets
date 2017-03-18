package concurrent.volatiledemo;

import java.util.concurrent.CountDownLatch;

/**
 * Created by weli on 6/7/16.
 */
public class CountDownLatchDemo {
    public static final void main(String[] args) throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final CountDownLatch mainLock = new CountDownLatch(3);

        for (String thread : new String[]{"t1", "t2", "t3"}) {
            createThread(thread, latch, mainLock).start();
        }

        mainLock.await();
        latch.countDown();
    }

    private static Thread createThread(final String threadName, final CountDownLatch latch, final CountDownLatch mainLock) {
        return new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println(threadName + "await...");
                    mainLock.countDown();
                    latch.await();
                    System.out.println(threadName + "go...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
