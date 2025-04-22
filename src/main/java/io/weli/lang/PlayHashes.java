package io.weli.lang;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class PlayHashes {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("Hello");
        set.add("World");
        set.add("Java");
        set.add("Programming");
        
        System.out.println("Set size: " + set.size());
        System.out.println("Set contents: " + set);

        Map<String, Integer> map = new HashMap<>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        
        System.out.println("Map size: " + map.size());
        System.out.println("Map contents: " + map);
    }
}
