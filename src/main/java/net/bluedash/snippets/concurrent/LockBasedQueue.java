package net.bluedash.snippets.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class LockBasedQueue<T> {
    int head, tail;
    T[] items;
    Lock lock;

    public LockBasedQueue(int capacity) {
        head = 0;
        tail = 0;
        lock = new ReentrantLock();
        items = (T[]) new Object[capacity];
    }

    public void enq(T x) throws FullException {
        lock.lock();
        try {
            if (tail - head == items.length) // enq is slower than deq because of this
                throw new FullException(); // linearization point
            items[tail % items.length] = x;
            System.out.print("+");
            tail++; // linearization point
        } finally {
            lock.unlock();
        }
    }

    public T deq() throws EmptyException {
        lock.lock();
        try {
            if (tail == head)
                throw new EmptyException(); // linearization point
            T x = items[head % items.length];
            System.out.print("-");
            head++; // linearization point
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        final LockBasedQueue<Integer> queue = new LockBasedQueue<Integer>(3);
        final int target = 6;
        Thread enqT = new Thread() {
            public void run() {
                int i = target;
                while (i > 0) {
                    try {
                        queue.enq(new Random().nextInt());
                        i--;
                    } catch (FullException e) {
                        System.out.print("O");
                    } finally {
                        try {
                            Thread.sleep(Math.abs(new Random().nextInt() % 100));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread deqT = new Thread() {
            public void run() {
                int i = target;
                while (i > 0) {
                    try {
                        queue.deq();
                        i--;
                    } catch (EmptyException e) {
                        System.out.print("X");
                    } finally {
                        try {
                            Thread.sleep(Math.abs(new Random().nextInt() % 200)); // let deqT wait longer because deq is faster than enq.
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        enqT.start(); // single thread enq
        deqT.start(); // single thread deq

        enqT.join();
        deqT.join();
    }
}


