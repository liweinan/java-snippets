package io.weli.misc;

import java.util.stream.Stream;

public class PP {
    public static void main(String[] args) {
        Stream.of("little", "red", "riding", "hood")
                .parallel()
                .map(s -> {
                    System.out.println("map: " + s + " " +
                            Thread.currentThread().getName());
                    return s + "_";
                })
                .filter(s -> {
                    System.out.println("filter: " + s + " " +
                            Thread.currentThread().getName());
                    return s.length() > 3;
                })
                .reduce((s1, s2) -> {
                    System.out.println("reducer: " + s1 + " " +
                            Thread.currentThread().getName());
                    return s1.length() > s2.length() ? s1 : s2;
                });
    }
}
