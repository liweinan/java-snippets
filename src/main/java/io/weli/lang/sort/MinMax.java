package io.weli.lang.sort;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;

class MinMax {

    /*
     * Complete the 'miniMaxSum' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static void miniMaxSum(List<Integer> arr) {
        // Write your code here
        // Integer[] _arr = new Integer[arr.size()];
        // arr.toArray(_arr);

        long max = 0;
        long min = Long.MAX_VALUE;
        long curr;

        for (int i = 0; i < arr.size(); i++) {
            curr = 0;
            for (int j = 0; j < arr.size(); j++) {
                if (j != i) { // bypass val at i
                    curr += arr.get(j); // sum the rest
                }
            }
            System.out.println("curr: " + curr);
            System.out.println("min: " + min);
            System.out.println("max: " + max);
            if (curr > max) {
                max = curr; // new max
                System.out.println("new max: " + max);
            }
            if (curr < min) {
                min = curr; // new min
                System.out.println("new min: " + min);
            }
        }

        System.out.printf("%s %s", String.valueOf(min), String.valueOf(max));
    }

}

