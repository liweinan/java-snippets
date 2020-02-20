package io.alchemystudio.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 5/14/16.
 */
public class InstanceCasts {
    public static void main(String[] args) {
        List<Object> objs = new ArrayList();
        List<String> strings = (List<String>) (List<?>) objs;
    }
}
