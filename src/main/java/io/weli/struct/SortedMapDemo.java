package io.weli.struct;

import java.util.TreeMap;

public class SortedMapDemo {
    public static void main(String[] args) {
         var map = new TreeMap<Integer, String>();

        map.put(2,"b");
        map.put(3,"a");
        map.put(1,"z");


        System.out.println(map.keySet());
        System.out.println(map.firstKey());
        System.out.println(map.firstEntry());
    }
}
