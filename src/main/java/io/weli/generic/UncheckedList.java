package io.weli.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 6/23/16.
 */
public class UncheckedList {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");
        
        acceptStringList(stringList);
    }

    private static void acceptStringList(List<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
    }
}
