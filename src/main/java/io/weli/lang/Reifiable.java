package io.weli.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 5/11/16.
 */
public class Reifiable {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        
        System.out.println("List size: " + list.size());
        System.out.println("List contents: " + list);
    }
}
