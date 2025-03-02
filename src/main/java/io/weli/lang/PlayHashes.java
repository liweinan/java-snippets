package io.weli.lang;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;

public class PlayHashes {
    public static void main(String[] args) {
        var set = new HashSet();

        set.add(1);
        set.add(2);
        set.add(3);
        set.add(3);

        Stream.of(set).forEach(System.out::println);

        var map = new HashMap();
        map.put(1, 1);
    }
}
