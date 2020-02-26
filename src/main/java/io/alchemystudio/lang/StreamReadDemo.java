package io.alchemystudio.lang;

import java.nio.file.Files;
import java.nio.file.Paths;

public class StreamReadDemo {
    // https://mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
    public static void main(String[] args) throws Exception {
        Files.list(Paths.get("./")).forEach(System.out::println);
    }
}
