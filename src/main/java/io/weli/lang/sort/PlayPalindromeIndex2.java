package io.weli.lang.sort;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PlayPalindromeIndex2 {

    // better solution: https://stackoverflow.com/questions/70511886/hackerrank-solving-palindrom-index-solution

    public static int palindromeIndex(String s) {

        int start = 0;
        int end = s.length() - 1;
        while (start < end && s.charAt(start) == s.charAt(end)) {
            start++;
            end--;
        }

        if (start >= end) return -1; // already a palindrome
        // We need to delete here
        if (isPalindrome(s, start + 1, end)) return start;
        if (isPalindrome(s, start, end - 1)) return end;
        return -1;
    }

    public static boolean isPalindrome(String s, int start, int end) {
        while (start < end && s.charAt(start) == s.charAt(end)) {
            start++;
            end--;
        }
        return start >= end;
    }

    public static void main(String[] args) {
        System.out.println(palindromeIndex("aaab"));
    }
}
