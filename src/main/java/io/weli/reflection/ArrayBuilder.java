package io.weli.reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ArrayBuilder {
    @SafeVarargs
    public static <T> List<T>[] createArrayOfLists(List<T>... lists) {
        @SuppressWarnings("unchecked")
        List<T>[] result = new List[lists.length];
        System.arraycopy(lists, 0, result, 0, lists.length);
        return result;
    }

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>(Arrays.asList("one", "two"));
        List<String> list2 = new ArrayList<>(Arrays.asList("three", "four"));
        
        @SuppressWarnings("unchecked")
        List<String>[] arrayOfLists = createArrayOfLists(list1, list2);
        
        for (List<String> list : arrayOfLists) {
            System.out.println(list);
        }
    }
}
