package io.alchemystudio.foo;

public class Bar {
    public static void main(String[] args) {
        int foo;

        if ((foo = something()) != 0) {
            System.out.println(foo);
            // blabla...
        }

        Object bar = null;

        if ((foo = something()) != 0) {
            System.out.println(foo);
            bar = new Object();
        }

        if (bar == null) {
            System.out.println(bar);
        }

    }

    private static int something() {
        return 2;
    }
}
