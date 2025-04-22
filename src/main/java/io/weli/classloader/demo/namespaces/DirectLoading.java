package io.weli.classloader.demo.namespaces;

import java.lang.reflect.Constructor;

@SuppressWarnings({"unchecked", "rawtypes"})
public class DirectLoading {
    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("io.weli.classloader.demo.namespaces.SomeClass");
        Constructor constructor = clazz.getDeclaredConstructor();
        Object obj = constructor.newInstance();
        System.out.println(obj);
    }
}
