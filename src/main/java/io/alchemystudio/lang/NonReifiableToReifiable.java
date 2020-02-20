package io.alchemystudio.lang;

import java.util.*;

/**
 * Created by weli on 5/27/16.
 */
public class NonReifiableToReifiable {
    interface Foo<T> {
        T bar();
    }

    static class StringTypedFoo implements Foo<String> {
        public String bar() {
            return null;
        }
    }


    static class StringTypedArrayList extends ArrayList<String> {}

    public static void main(String[] args) {
        StringTypedArrayList list = new StringTypedArrayList();
        list.add("cafebabe");
    }
}
