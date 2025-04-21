package io.weli.hackerrank;

import java.util.Arrays;

// https://www.hackerrank.com/challenges/java-string-tokens/problem
public class JavaStringTokens {
    public static void main(String[] args) {
//        String in = "He is a very very good boy, isn't he?";
//        String in = "           YES      leading spaces        are valid,    problemsetters are         evillllll";
        String in = "                        ";
        if (in.trim().equals("")) {
            System.out.println("0");
        } else {
            String[] out = in.trim().split("[ !,?._'@]+");
            System.out.println(out.length);
            Arrays.stream(out).forEach(System.out::println);
        }
    }
}
