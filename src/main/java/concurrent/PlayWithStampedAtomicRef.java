package concurrent;

import java.util.concurrent.atomic.AtomicStampedReference;

public class PlayWithStampedAtomicRef {
    static int stampVal = 1;
    static AtomicStampedReference<Person> s = new AtomicStampedReference<Person>(new Person(20), stampVal);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 1; i <= 3; i++) {
                    System.out.println("stamp value for first thread:" + stampVal);
                    s.compareAndSet(s.getReference(), new Person(s.getReference().age + 10), stampVal, ++stampVal);
                    System.out.println("Atomic Check by first thread: " + Thread.currentThread().getName() + " is " + s.getReference().age);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 3; i++) {
                    System.out.println("stamp value for second thread:" + stampVal);
                    s.compareAndSet(s.getReference(), new Person(s.getReference().age + 10), stampVal, ++stampVal);
                    System.out.println("Atomic Check by second thread : " + Thread.currentThread().getName() + " is " + s.getReference().age);
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final value: " + s.getReference().age);

    }

    static class Person {
        int age;
        public Person(int i) {
            age = i;
        }
    }
}

