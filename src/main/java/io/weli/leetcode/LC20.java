package io.weli.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

// https://leetcode.com/problems/valid-parentheses/?envType=study-plan-v2&envId=top-interview-150
public class LC20 {
    public static boolean isValid(String s) {

        Stack<Character> stack = new Stack();


        if (s.equals(""))
            return true;

        if (s.length() % 2 != 0)
            return false;

        char[] leftC = new char[]{'(', '{', '['};
        char[] rightC = new char[]{')', '}', ']'};

        Map<Character, Integer> left = new HashMap<>();
        Map<Character, Integer> right = new HashMap<>();


        for (int i = 0; i < leftC.length; i++) {
            left.put(leftC[i], i);
        }

        for (int i = 0; i < rightC.length; i++) {
            right.put(rightC[i], i);
        }


        for (int i = 0; i < s.length(); i++) {
            if (left.containsKey(s.charAt(i))) {
                stack.push(s.charAt(i));
            } else if (right.containsKey(s.charAt(i))) {
                if (stack.isEmpty())
                    return false;
                char currLeft = stack.pop();
                if (left.get(currLeft) != right.get(s.charAt(i)))
                    return false;
            }
        }

        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


}
