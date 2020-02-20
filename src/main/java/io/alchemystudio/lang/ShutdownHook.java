package io.alchemystudio.lang;

// https://www.baeldung.com/jvm-shutdown-hooks
public class ShutdownHook {
    public static void main(String[] args) {
        Thread printingHook = new Thread(() -> System.out.println("In the middle of a shutdown"));
        Runtime.getRuntime().addShutdownHook(printingHook);
    }
}
