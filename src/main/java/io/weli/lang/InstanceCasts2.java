package io.weli.lang;

import java.util.Collection;
import java.util.List;

/**
 * Created by weli on 5/14/16.
 */
public class InstanceCasts2 {

    public static <T> List<T> foo(Collection<T> coll) {
        return (List<T>) coll;
    }

    public static <T, U extends T> List<T> bar(Collection<U> coll) {
        return (List<T>) coll;
    }
}
