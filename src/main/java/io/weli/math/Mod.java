package io.weli.math;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Mod {
    public static void main(String[] args) {
        System.out.println(1 % 2);
        System.out.println(0 % 2);

        List<Integer>[] lists = new List[10];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new ArrayList<>();
        }
        System.out.println(lists.length);

        System.out.println(String.valueOf("abcdefg"));
    }
}
