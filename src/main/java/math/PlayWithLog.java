package math;

import java.util.concurrent.ThreadLocalRandom;

public class PlayWithLog {
    public static void main(String[] args) {

        // 返回是从0到1的区间的数字。
        System.out.println(ThreadLocalRandom.current().nextDouble());

        System.out.println(Math.log10(1));

        System.out.println(Math.log10(0.1));

        // log()方法默认是以e为底。
        // 1 - 0.x 的区间是 (0..1)
        // 其log值肯定是负数。
        System.out.println(Math.log(1 - ThreadLocalRandom.current().nextDouble()));
    }
}
