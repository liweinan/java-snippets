package io.alchemystudio.lang.eight;

import java.util.Arrays;
import java.util.List;

/**
 * Created by weli on 26/07/2017.
 */
public class PlayWithStream {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("Apple", "Banana", "Tomato", "Plum", "Almond");
        list.stream().filter(s -> s.startsWith("A"))
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}
