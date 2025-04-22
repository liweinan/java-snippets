package io.weli.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CheckedList {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        List checkedStrings = Collections.checkedList(strings, String.class);
        checkedStrings.add("Hello");
        checkedStrings.add("World");
        System.out.println(checkedStrings);
    }
}
