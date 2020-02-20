package io.alchemystudio.lang.autoclosable;

public class FooClass implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.println("close() method called");
    }
}