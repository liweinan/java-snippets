package io.alchemystudio.lang;

import java.nio.file.Files;
import java.nio.file.Paths;

// https://www.baeldung.com/java-system-get-property-vs-system-getenv
public class JavaSystemProperty {
    public static void main(String[] args) {
        System.getenv().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });

    }
}
