package io.alchemystudio.lang;

import java.util.Map;

public class EntrySetDemo {
    public static void main(String[] args) {
        Map m = Map.of("foo", "bar");

        var entry = (Map.Entry) m.entrySet().iterator().next();
        System.out.println(entry.getKey());
        System.out.println(entry.getValue());
    }
}
