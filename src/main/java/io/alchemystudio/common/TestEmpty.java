package io.alchemystudio.common;

public class TestEmpty {
    static void passNull(Object nil) {
        if (nil == null)
            nil = new Object();
        System.out.println(nil);
    }

    public static void main(String[] args) {
        passNull(null);
    }
}
