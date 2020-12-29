package io.weli.classloader.demo.namespaces;

import io.weli.classloader.SimpleClassLoader2;
import io.weli.classloader.demo.impl.ProductImpl;

import java.lang.reflect.Method;

public class ReflectiveUsage {
    public static String getClazzPath(Class clazz) {
        String clazzToPath = clazz.getName().replaceAll("\\.", "/") + ".class";
        return "target/classes/" + clazzToPath;
    }

    public static void main(String[] args) throws Exception {
        String clazzPath = getClazzPath(ProductImpl.class);

        ClassLoader loader = new SimpleClassLoader2(clazzPath);
        Class clazz = loader.loadClass(ProductImpl.class.getName());

        Method method = clazz.getMethod("show");
        method.invoke(clazz.getDeclaredConstructor().newInstance());
    }
}
