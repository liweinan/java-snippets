package io.weli.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraysPitfall {
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3};
        System.out.println("array / " + array.getClass().getCanonicalName());
        var notSupportAddArray = Arrays.asList(array);

        // java.util.Arrays.ArrayList
        System.out.println("notSupportAddArray / " + notSupportAddArray.getClass().getCanonicalName() + ": " + notSupportAddArray);
        try {
            notSupportAddArray.add(4);
        } catch (UnsupportedOperationException e) {
            System.out.println("Expected: " + e);
        }

        var encapsulatedArray = new ArrayList<>(notSupportAddArray);
        encapsulatedArray.add(4);
        // java.util.ArrayList
        System.out.println("encapsulatedArray / " + encapsulatedArray.getClass().getCanonicalName() + ": " + encapsulatedArray);

        // raw type array is not supported by `Arrays.asList`
        var array2 = new int[]{1, 2, 3};
        System.out.println("array2: " + array2.getClass().getCanonicalName());
        // https://stackoverflow.com/questions/1467913/arrays-aslist-not-working-as-it-should
        // `array2` is treated as a single object
        var notSupportAddArray2 = Arrays.asList(array2);
        System.out.println("notSupportAddArray2 / " + notSupportAddArray2.getClass().getCanonicalName() + ": " + notSupportAddArray2);

        // convert it to `List` firstly.
        var notSupportAddArray3 = Arrays.asList(asList(array2));
        System.out.println("notSupportAddArray3 / " + notSupportAddArray3.getClass().getCanonicalName() + ": " + notSupportAddArray3);

    }

    public static List<Integer> asList(int[] a) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < a.length && list.add(a[i]); i++) ;
        return list;
    }

}
