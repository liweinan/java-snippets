package io.alchemystudio.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayWithLambda {
    public static void main(String[] args) {
        List results = Arrays.asList(1, 2, 3).stream().map(x -> x * x).collect(Collectors.toList());
        results.forEach(System.out::println);
    }
}
