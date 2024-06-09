package io.weli.spring;

import java.lang.reflect.Array;

public class PlayWithSpring {
    public static void main(String[] args) throws Exception {
        int[] foos = new int[]{1, 2, 3};
        System.out.println(foos.getClass());
        System.out.println(Array.newInstance(foos.getClass(), 0).getClass());
    }
}
