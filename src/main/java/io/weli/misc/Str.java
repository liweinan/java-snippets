package io.weli.misc;

import java.math.BigInteger;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Str {

    static class SuggestionBox {
        private void onSelection() {
            //Do action when item has been selected.
            System.out.println("Item selected.");
        }

        public void onRemoveSelection() {
            //Do action when item selection has been removed.
            System.out.println("Item removed.");
        }
    }

    public static void main(String[] args) {
        var a = String.valueOf(1);
//        var b = String.toString(1);
        var c = Optional.of("Hi").get();
//        var d = new Integer(1).toString();
        var e = String.format("Hello %s!", 1);

        List<String> list = Arrays.asList("dog", "over", "good");
        System.out.println(list.stream().reduce(new String(), (s1, s2) -> s1 + s2.charAt(0), (c1, c2) -> c1 += c2));



        SuggestionBox officeSuggestionBox = new SuggestionBox() {
            public void onSelection() {
                //Do action when item has been selected.
                System.out.println("Office selected.");
            }

            public void onRemoveSelection() {
                //Do action when item selection has been removed.
                onSelection();
                System.out.println("Office removed.");
            }
        };
        officeSuggestionBox.onRemoveSelection();
        ZoneId zoneId = ZoneId.systemDefault();

        print("My", "Test");
        print(new Employee<Integer, String>(1, "One"), new Employee<Integer, String>(2, "Two"));
    }

    @SafeVarargs
    public static <T> void print(T... a) {
        for (T t : a) {
            System.out.println(t);
        }
    }

    static class Employee<T1, T2> {
        public Employee(int id, String name) {
        }
    }
}

