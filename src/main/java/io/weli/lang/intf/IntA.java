package io.weli.lang.intf;

public interface IntA {
    default void print() {
        System.out.println(this);
    }
}
