package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ArrayBasedSpinLock implements Lock {

    ThreadLocal<Integer> index = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    AtomicInteger tail;

    volatile boolean[] flag;

    int size;

    public ArrayBasedSpinLock(int size) {
        this.size = size;
        tail = new AtomicInteger(0);
        flag = new boolean[size];
        flag[0] = true;
    }

    public void lock() {
        int slot = tail.getAndIncrement() % size;
        index.set(slot);
        while (!flag[slot]) {
        }
        ;
    }

    public void unlock() {
        int slot = index.get();
        flag[slot] = false;
        flag[(slot + 1) % size] = true;
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
