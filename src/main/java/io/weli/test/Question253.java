package io.weli.test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Question253 {
    static List<Person> people = Arrays.asList(
            new Person("Bob", 1), new Person(2), new Person("Jane", 3));
    static int x;

    public static void main(String[] args) {
        people.stream()
                .reduce((e1, e2) -> {
                    x = e1.id;
                    if (e1.id > e2.id) {
                        System.out.println(e1);
                        return e1;
                    }
                    x = e2.id;
                    System.out.println(e2);
                    return e2;
                })
                .flatMap(e -> Optional.ofNullable(e.name))
                .map(y -> new Person(y, x))
                .ifPresent(System.out::println);
    }
}
