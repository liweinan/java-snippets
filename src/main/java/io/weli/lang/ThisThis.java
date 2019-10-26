package io.weli.lang;

import io.weli.reflection.Clazz;

public class ThisThis {
    public ThisThis thisthis() {
        return ThisThis.this;
    }

    public Class supersuper() {
        return ThisThis.super.getClass();
    }

    public static void main(String[] args) throws Exception {
        Class c = ThisThis.class;
        ThisThis t = new ThisThis().thisthis();
        Class s = new ThisThis().getClass();
    }
}
