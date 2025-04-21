package io.weli.leetcode;

import java.util.concurrent.CountDownLatch;

public class LC1114 {
    static class Foo {
        private CountDownLatch m1 = new CountDownLatch(1);
        private CountDownLatch m2 = new CountDownLatch(1);

        public Foo() {

        }

        public void first(Runnable printFirst) throws InterruptedException {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            m1.countDown();
        }

        public void second(Runnable printSecond) throws InterruptedException {

            m1.await();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            m2.countDown();
        }

        public void third(Runnable printThird) throws InterruptedException {
           m2.await();

            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }

    public static void main(String[] args) throws Exception {
        Foo f = new Foo();
        f.first(() -> println("A"));
        f.second(() -> println("B"));
        f.third(() -> println("C"));
    }

    private static void println(String a) {
        System.out.println(a);
    }
}
