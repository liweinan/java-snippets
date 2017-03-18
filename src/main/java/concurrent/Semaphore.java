package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Semaphore {
    final int capacity;
    int state;
    Lock lock;
    Condition condition;

    public Semaphore(int capacity) {
        this.capacity = capacity;
        state = 0;
        lock = new MyReentrantLock();
        condition = lock.newCondition();
    }

    public void acquire() {
        lock.lock();
        try {
            while (state == capacity) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            state++;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            state--;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
