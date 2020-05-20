package io.alchemystudio.classloader.demo.namespaces;

import io.alchemystudio.classloader.SimpleClassLoader2;
import io.alchemystudio.classloader.demo.impl.ProductImpl;

public class TwoLoaders {

    public static String getClazzPath(Class clazz) {
        String clazzToPath = clazz.getName().replaceAll("\\.", "/") + ".class";
        return "target/classes/" + clazzToPath;
    }

    public static void main(String[] args) throws Exception {

        String clazzPath = getClazzPath(ProductImpl.class);

        ClassLoader loaderX = new SimpleClassLoader2(clazzPath);
        ClassLoader loaderY = new SimpleClassLoader2(clazzPath);

        Class clazzX = loaderX.loadClass(ProductImpl.class.getName());
        Class clazzY = loaderY.loadClass(ProductImpl.class.getName());

        System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
        System.out.println("X: " + clazzX.getName());;
        System.out.println("Y: " + clazzY.getName());;
        System.out.println("X = Y? " + (clazzX == clazzY));

        System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");

        Object x = clazzX.getDeclaredConstructor().newInstance();
        System.out.println(x.getClass());

        System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");

        try {
            ProductImpl y = (ProductImpl) x;
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }
}
