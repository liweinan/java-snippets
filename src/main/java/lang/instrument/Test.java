package lang.instrument;

import jdk.internal.org.objectweb.asm.util.ASMifier;

/**
 * Created by weli on 14/05/2017.
 */
public class Test {
    public static void main(String[] args) {
        printOne();
        printOne();
        printTwo();
    }

    public static void printOne() {
        System.out.println("Hello World");
    }

    public static void printTwo() {
        printOne();
        printOne();
    }
}
