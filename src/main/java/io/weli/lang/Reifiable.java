package io.weli.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 5/11/16.
 */
public class Reifiable {

    public static void main(String[] args) {
        List lst = new ArrayList();
        lst.add(1);
        lst.add("S");

        List<?> list = lst;
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
