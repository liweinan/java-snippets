package io.alchemystudio.lang;

/**
 * Created by weli on 5/18/16.
 */
public class PlayWithExceptions {

    public static void foo() throws RuntimeException {
    }

    public static void bar() throws Error {
        throw new StackOverflowError();
    }

    public static void xyz() throws Exception {

    }

    public static void main(String[] args) {
        foo();
        bar();

        try {
            xyz(); // checked exception
        } catch (Exception e) {
            // deal with the Exception
        }
    }
}
