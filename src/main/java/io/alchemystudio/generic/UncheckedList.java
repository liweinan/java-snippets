package io.alchemystudio.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 6/23/16.
 */
public class UncheckedList {

    public static void acceptStringList(List<String> list) {
        for (String item : list) {
            System.out.println(item);
        }
    }

    public static void main(String[] args) throws Exception {
        List typeUnsafeList = new ArrayList();
        typeUnsafeList.add(1);
        typeUnsafeList.add("abc");
        acceptStringList(typeUnsafeList);
    }
}
