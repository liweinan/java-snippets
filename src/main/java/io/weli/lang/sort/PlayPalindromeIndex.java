package io.weli.lang.sort;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PlayPalindromeIndex {

    public static int palindromeIndex(String s) {
        // Write your code here
        if (s.length() < 2) return -1;

        int[] chars = s.chars().toArray();

        if (isPalindrome(chars)) {
            return -1;
        }

        for (int i = 0; i < s.length(); i++) {
            int idx = i;
            System.out.println("idx: " + idx);

            System.out.print("chars: ");
            s.chars().forEach(_i -> System.out.print(_i + " "));
            System.out.println();

            int[] subChars =
                    IntStream.range(0, chars.length).filter(_x -> idx != _x)
                            .map(_x -> {
                                System.out.println("_x: "+ _x);
                                System.out.println("chars[_x] "+ chars[_x]);
                                return chars[_x];
                            }).toArray();


            // https://stackoverflow.com/questions/73508326/how-to-print-individual-elements-of-an-int-using-a-stream-in-java
            System.out.print("subChars: ");
            Arrays.stream(subChars).forEach(_i -> System.out.print(_i + " "));
            System.out.println();

            if (isPalindrome(subChars))
                return idx;
        }

        return -1;
    }

    public static boolean isPalindrome(int[] chars) {
        int i = 0;
        int j = chars.length - 1;

        while (i < j) {
            if (chars[i] != chars[j]) {
                return false;
            }

            i++;
            j--;
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(palindromeIndex("aaab"));
    }
}
