package io.weli.lang.intf;

public class C1 implements IntA, IntB {

    // must override this default method to avoid conflict.
    @Override
    public void print() {
        System.out.println(this);
    }
}
