package net.bluedash.snippets.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Weinan Li
 * @date 08 05 2012
 */
public class Cell2<T> {
    private final T value = null;

    private static List<Object> values = new ArrayList<Object>();
    // ok public Cell(T value) { this.value=value; values.add(value); } public T getValue() { return value; }

    public static List<Object> getValues() {
        return values;
    } // ok
}