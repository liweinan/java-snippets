package io.weli.leetcode;

import java.util.ArrayList;
import java.util.List;

public class LC17 {
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList();

        if (digits == null || digits.length() == 0) {
            return result;
        }

        String[] map = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"}; // 0, 1 => ""

        backtrack(result, map, digits, "", 0);
        return result;
    }

    private static void backtrack(List<String> result, String[] map, String digits, String curr, int idx) {
        if (curr.length() == digits.length()) {
            result.add(curr);
            return;
        }

        int digit = digits.charAt(idx) - '0';
        String letters = map[digit];

        for (int i = 0; i < letters.length(); i++) {
            backtrack(result, map, digits, curr + letters.charAt(i), idx + 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));
    }
}