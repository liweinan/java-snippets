package io.weli.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by weli on 5/14/16.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class InstanceCasts2 {

    public static void main(String[] args) {
        Collection<String> strings = new ArrayList<>();
        List<String> list = (List<String>) strings;
        System.out.println(list);
    }

    public static <T> List<T> foo(Collection<T> coll) {
        return (List<T>) coll;
    }

    public static <T, U extends T> List<T> bar(Collection<U> coll) {
        return (List<T>) coll;
    }
}
