package io.weli.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 4/20/16.
 */
public class SubtypeAndWildcard {


    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<Integer>();

        ints.add(1);
        ints.add(2);

//        List<Number> lst = ints; <-- compile time error
//        lst.add(3.14); <-- type not safe

    }
}
