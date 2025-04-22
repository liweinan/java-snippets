package io.weli.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 4/26/16.
 */
public class WildcardUsages {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");
        
        printList(stringList);
    }
    
    private static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }
}
