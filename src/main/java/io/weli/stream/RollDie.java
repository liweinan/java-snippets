package io.weli.stream;

import java.security.SecureRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RollDie {
    public static void main(String[] args) {
        SecureRandom randomNumbers = new SecureRandom();

        int[] freq = new int[7];

        for (int roll = 1; roll <= 6000000; roll++)
            ++freq[1 + randomNumbers.nextInt(6)];

        System.out.printf("%s%10s%n", "Face", "Frequency");

        for (int face = 1; face < freq.length; face++)
            System.out.printf("%4d%10d%n", face, freq[face]);


        // ---------------------------------------------------------
        // stream version
        System.out.printf("%-6s%s%n", "Face", "Frequency");
        randomNumbers.ints(6_000_000, 1, 7)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((face, frequency) -> System.out.printf("%-6d%d%n", face, frequency));
    }
}
