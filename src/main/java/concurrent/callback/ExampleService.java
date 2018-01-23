package concurrent.callback;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ExampleService {

    String work() {
        sleep(7000, TimeUnit.MILLISECONDS); /* Pretend to be busy... */
        char[] str = new char[5];
        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int idx = 0; idx < str.length; ++idx)
            str[idx] = (char) ('A' + current.nextInt(26));
        String msg = new String(str);
        System.out.println("Generated message: " + msg);
        return msg;
    }

    public static void sleep(long average, TimeUnit unit) {
        String name = Thread.currentThread().getName();

        long timeout = Math.min(exponential(average), Math.multiplyExact(10, average)) / 10;

        System.out.printf("%s sleeping %d %s...%n", name, timeout, unit);
        try {
            unit.sleep(timeout);
            System.out.println(name + " awoke.");
        } catch (InterruptedException abort) {
            Thread.currentThread().interrupt();
            System.out.println(name + " interrupted.");
        }
    }

    public static long exponential(long avg) {
        return (long) (avg * -Math.log(1 - ThreadLocalRandom.current().nextDouble()));
    }

}
