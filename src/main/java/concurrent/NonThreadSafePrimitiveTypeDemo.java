package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class NonThreadSafePrimitiveTypeDemo {

    public static void main(String[] args) throws InterruptedException {
        final long[] i = {10000};
        final Lock lock = new ReentrantLock();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    i[0] = 222222;
                    lock.unlock();
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    i[0] = 10000;
                    lock.unlock();
                }
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(t1);
        executor.submit(t2);

        while (true) {
            lock.lock();
            if (i[0] != 10000 && i[0] != 222222) {
                executor.shutdownNow();
                System.out.println("CONFLICT FOUND.");
                return;
            }
            lock.unlock();
        }
    }
}
