package io.weli.lang;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
// https://www.baeldung.com/java-9-compact-string
@SuppressWarnings({"unchecked", "rawtypes"})
public class CompactStringDemo {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        List strings = IntStream.rangeClosed(1, 10_000_000)
                .mapToObj(Integer::toString)
                .collect(toList());

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println(
                "Generated " + strings.size() + " strings in " + totalTime + " ms.");

        startTime = System.currentTimeMillis();

        String appended = (String) strings.stream()
                .limit(100_000)
                .reduce("", (l, r) -> l.toString() + r.toString());

        totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Created string of length " + appended.length()
                + " in " + totalTime + " ms.");

        String s1 = "Hello";
        String s2 = "World";
        String result = Stream.of(s1, s2)
                .reduce("", (a, b) -> a + b);
        System.out.println(result);
    }
}
