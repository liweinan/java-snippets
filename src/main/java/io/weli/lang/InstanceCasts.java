package io.weli.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 5/14/16.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class InstanceCasts {
    public static void main(String[] args) {
        List<Object> objects = new ArrayList<>();
        List<?> wildcard = new ArrayList<>();
        List<String> strings = (List<String>) wildcard;
        System.out.println(strings);
    }
}
