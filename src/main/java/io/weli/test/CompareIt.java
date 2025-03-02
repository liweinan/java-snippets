package io.weli.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class CompareIt {
    public static void main(String[] args) {
        List<String> letters = new ArrayList<String>(
                Arrays.asList("F", "E", "D", "B", "E", "A", "C", "G"));
        Predicate<String> p1 = s -> s.compareTo("C") > 0;
        Predicate<String> p2 = s -> s.equals("B");
        letters.removeIf(p1.negate().or(p2));
        System.out.println(letters);
        letters.sort((s1, s2) -> s1.compareTo(s2));
        System.out.println(letters);
    }
}
