package io.weli.lang.intf;

public interface IntB {
    default void print() {
        System.out.println(this);
    }
}
