package io.weli.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 6/22/16.
 */
public class UnsafeStringList extends ArrayList<String> {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        List<String> list = new UnsafeStringList();
        list.add("Hello");
        list.add("World");
        System.out.println(list);
    }

    public static UnsafeStringList get() {
        UnsafeStringList list = new UnsafeStringList();
        list.add("a");
        list.add("1");
        return list;
    }
}
