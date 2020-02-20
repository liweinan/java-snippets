package io.alchemystudio.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 5/22/16.
 */
public class CovariantAndInvariant {

    public static void main(String[] args) {
        /*
         * Arrays are covariant
         */
        Integer[] ints = new Integer[] {1, 2, 3};
        Number[] nums = ints;
        nums[2] = 3.14; // java.io.weli.lang.ArrayStoreException

        /*
         * Generics are invariant
         */
        List<String> strings = new ArrayList();
        // compile time error
//        List<Object> objects = strings;

        /*
         * Generic array creation is not allowed
         */
        // compile time error
//        List<String>[] stringLists = new List<String>[] {};
    }
}
