package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class MCSQueueSpinLock implements Lock {

    AtomicReference<QNode> tail;

    ThreadLocal<QNode> node;

    public MCSQueueSpinLock() {
        tail = new AtomicReference<QNode>(null);
        node = new ThreadLocal<QNode>() {
            @Override
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }

    public void lock() {
        QNode myNode = node.get();
        QNode prev = tail.getAndSet(myNode);
        if (prev != null) {
            myNode.locked = true;
            prev.next = myNode;
            while (myNode.locked) {
            }
        }
    }

    public void unlock() {
        QNode myNode = node.get();
        if (myNode.next == null) {
            if (tail.compareAndSet(myNode, null))
                return;
            while (myNode.next == null) {
            }
        }
        myNode.next.locked = false;
        myNode.next = null;
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
