package io.weli.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weli on 4/15/16.
 */
public class Reification {

    public static <E> E foo() {
        return null;
    }

    public static List<?> bar() {
        return null;
    }

    public static void main(String[] args) throws Exception {
        Method fooMethod = Reification.class.getMethod("foo", (Class<?>) null);
        System.out.println(fooMethod);
        System.out.println(fooMethod.getReturnType()); // reified type
        System.out.println(fooMethod.getGenericReturnType());
        System.out.println(fooMethod.getGenericReturnType().getClass());

        Method barMethod = Reification.class.getMethod("bar", (Class<?>) null);
        System.out.println(barMethod);
        System.out.println(barMethod.getReturnType());

        List<Integer> list = new ArrayList<Integer>();
        System.out.println("true");
    }
}
