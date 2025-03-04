package io.weli.stream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StreamOfLines {
    public static void main(String[] args) throws Exception {
        Pattern pattern = Pattern.compile("\\s+");

        Map<String, Long> wordCounts = Files.lines(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()))
                .map(line -> line.replaceAll("(?!')\\p{P}", ""))
                .flatMap(pattern::splitAsStream)
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));

        System.out.println("wordCounts: " + wordCounts);
        wordCounts.forEach((key, value) -> System.out.println(key + " : " + value));
    }
}
