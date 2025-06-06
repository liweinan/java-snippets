package io.weli.testdome;

import java.util.*;

// https://www.testdome.com/library?page=1&skillArea=30&questionId=135032
public class MathExpression {

    public static boolean isBalanced(String parentheses) {
        Stack<Character> stack = new Stack<>();
        Set<Character> opening = new HashSet<>(Arrays.asList('(', '[', '{'));
        Set<Character> closing = new HashSet<>(Arrays.asList(')', ']', '}'));
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');

        for (char c : parentheses.toCharArray()) {
            System.out.println("current c: " + c);
            if (opening.contains(c)) {
                stack.push(c);
            } else if (closing.contains(c)) {
                if (stack.isEmpty() || stack.peek() != pairs.get(c)) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    public static boolean isBalanced2(String parentheses) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');

        for (char c : parentheses.toCharArray()) {
            System.out.println("current c: " + c);
            if (pairs.containsValue(c)) {
                stack.push(c);
            } else if (pairs.containsKey(c)) {
                if (stack.isEmpty() || stack.peek() != pairs.get(c)) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String[] expressions = {
//            "(3+5)*(2-4)",
//            "{[()]}",
            "[3+5x(4-1]-39]"
        };

        for (String expr : expressions) {
            System.out.printf("Expression: %s\tBalanced: %b\n", expr, isBalanced(expr));
        }

//        System.out.println("\n----------------------------------------\n");
//
//        for (String expr : expressions) {
//            System.out.printf("Expression: %s\tBalanced2: %b\n", expr, isBalanced2(expr));
//        }
    }
}