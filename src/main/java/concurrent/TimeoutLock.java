package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class TimeoutLock implements Lock {

    static final QNode AVAILABLE = new QNode();
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> node;

    static class QNode {
        public QNode prev = null;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long start = System.currentTimeMillis();
        long patience = TimeUnit.MILLISECONDS.convert(time, unit);

        // By default prev = null
        // When a node's prev field is null, the associated thread has either not acquired the lock
        // or has not released it yet.
        QNode myNode = new QNode();
        node.set(myNode);

        QNode previousNode = tail.getAndSet(myNode);

        // If tail was null, then the current thread is now the tail(or the first one
        // requesting from tail) and can acquire the lock;
        // If the previous node's predecessor is set to AVAILABLE, that means the previous node
        // has released the lock. So the current thread can acquire the lock also.
        if (previousNode == null || previousNode.prev == AVAILABLE) {
            return true;
        }

        // Wait-lock cycle
        while (System.currentTimeMillis() - start < patience) {
            QNode predecessorOfPreviousNode = previousNode.prev;
            if (predecessorOfPreviousNode == AVAILABLE) {
                return true;
            } else if (predecessorOfPreviousNode != null) { // If it refers to some other node,
                // that means the associated thread has abandoned the lock request and time out,
                // so the thread owning the successor node should wait on the abandoned node's
                // predecessor.
                previousNode = predecessorOfPreviousNode;
            } else {
                // just wait spinning
            }
        }

        // timeout
        if (!tail.compareAndSet(myNode, previousNode)) // If the current node is not the tail,
            // it has a successor, so it needs to abandon the lock request and remove itself
            // from the requesting queue;
            myNode.prev = previousNode;

        return false;
    }

    public void unlock() {
        QNode myNode = node.get();
        if (!tail.compareAndSet(myNode, null)) // if the current node is tail, all requests are severed.
            myNode.prev = AVAILABLE; // if the current node is not tail, releasing the lock by pointing prev to AVAILABLE.
    }


    public boolean tryLock() {
        try {
            return tryLock(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void lock() {
        try {
            tryLock(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            while (true) {
            } // spin forever
        }
    }

    public void lockInterruptibly() throws InterruptedException {
        tryLock(1, TimeUnit.SECONDS);
    }

    public Condition newCondition() {
        return null;
    }
}
