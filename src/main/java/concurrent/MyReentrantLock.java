package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class MyReentrantLock implements Lock {

    Lock lock;
    Condition condition;
    long owner, holdCount;

    public MyReentrantLock() {
        lock = new MCSQueueSpinLock();
        condition = lock.newCondition();
        owner = 0;
        holdCount = 0;
    }

    public void lock() {
        long me = Thread.currentThread().getId();
        lock.lock();
        try {
            if (owner == me) {
                holdCount++;
                return;
            }
            while (holdCount != 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            owner = me;
            holdCount = 1;
        } finally {
            lock.unlock();
        }
    }

    public void unlock() {
        lock.lock();
        try {
            if (holdCount == 0 || owner != Thread.currentThread().getId())
                throw new IllegalMonitorStateException();
            holdCount--;
            if (holdCount == 0) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    public Condition newCondition() {
        return null;
    }
}
