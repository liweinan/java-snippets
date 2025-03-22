package io.weli.misc;

import io.weli.lang.subclass.I;

@FunctionalInterface
public interface Math {
    int DoMath(int x, int y);

    default String Truncate(int x, int y) {
        return String.valueOf(Integer.sum(x, y)).substring(0, 1);
    }
}
