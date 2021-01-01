package io.weli.lc;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class LC1116 {
    static class ZeroEvenOdd {
        private int n;
        private Semaphore zero = new Semaphore(1);
        private Semaphore even = new Semaphore(0);
        private Semaphore odd = new Semaphore(0);

        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                zero.acquire();
                printNumber.accept(0);
                if (i % 2 == 1) {
                    odd.release();
                } else {
                    even.release();
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            for (int i = 2; i <= n; i += 2) {
                even.acquire();
                printNumber.accept(i);
                zero.release();
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i += 2) {
                odd.acquire();
                printNumber.accept(i);
                zero.release();
            }
        }
    }

// 作者：dan-xie-6
// 链接：https://leetcode-cn.com/problems/print-zero-even-odd/solution/xin-hao-liang-jie-jue-by-dan-xie-6/
// 来源：力扣（LeetCode）
// 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    public static void main(String[] args) throws Exception {
//        CountDownLatch c = new CountDownLatch(2);
//        c.countDown();
//        System.out.println(c.getCount());
//        c.countDown();
//        System.out.println(c.getCount());
//        c.countDown();
//        System.out.println(c.getCount());

        ZeroEvenOdd lc = new ZeroEvenOdd(8);
        new Thread(() -> {
            try {
                lc.zero(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                lc.odd(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                lc.even(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
