package io.weli.hackerrank;

// https://www.hackerrank.com/contests/software-engineer-prep-kit/challenges/check-palindrome-filter-non-letters/problem?isFullScreen=true
public class CheckPalindromebyFilteringNonLetters {


    public static boolean isAlphabeticPalindrome(String code) {
        // Write your code here
        int left = 0;
        int right = code.length() - 1;

        while (left < right) {
            char lc = code.charAt(left);
            char rc = code.charAt(right);
            if (!Character.isLetter(lc)) {
                left++;
                continue;
            }

            if (!Character.isLetter(rc)) {
                right--;
                continue;
            }

            if (Character.toLowerCase(lc) !=Character.toLowerCase(rc)){
                return false;
            }

            left++;
            right--;
        }

        return true;


    }

    public static void main(String[] args) {

    }
}
