package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by weli on 15/04/2017.
 */
public class AB {
    static int a() throws Exception {
        final int base = 42;

        final AtomicInteger x = new AtomicInteger();
        final AtomicInteger y = new AtomicInteger();

        Thread ta = new Thread() {
            @Override
            public void run() {x.set(base * 2);
            }
        };

        Thread tb = new Thread() {
            @Override
            public void run() {
                y.set(base + 1);
            }
        };

        ta.start();
        tb.start();

        ta.join();
        tb.join();

        int result = x.get() + y.get();
        return result;
    }

    static int b() throws Exception {
        return a() + 2;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(b());
    }
}
