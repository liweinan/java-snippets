package io.weli.lambda;

import java.util.Arrays;
import java.util.List;

public class DoubleColonDemo {

    public static void printIt(Object obj) {
        System.out.println(obj.toString().toUpperCase());
    }


    public static void main(String[] args) {
        List<String> numbers = Arrays.asList("Red", "Black");

        numbers.forEach(System.out::println);

        numbers.forEach(DoubleColonDemo::printIt);

        numbers.forEach(o -> {
            System.out.println(o.toLowerCase());
        });
    }
}
