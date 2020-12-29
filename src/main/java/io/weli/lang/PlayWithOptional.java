package io.weli.lang;

import java.util.Optional;

public class PlayWithOptional {
    public static void main(String[] args) {
        Optional<String> str = Optional.of("Hello, world!");

        String out = str.isEmpty() ? "NONE" : str.get().toUpperCase();
        System.out.println(out);
    }
}
