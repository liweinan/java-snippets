package io.weli.misc;

import java.math.BigInteger;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {
        var l = new LinkedList<Number>();
        l.add(1F + 2D);
        l.add(new BigInteger(new byte[]{0x1, 0x2, 0x3, 0x4}));
        l.add(1);
//        l.add("12F".transform(Integer::parseInt));
    }
}
