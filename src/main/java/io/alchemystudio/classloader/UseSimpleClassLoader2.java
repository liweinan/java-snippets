package io.alchemystudio.classloader;

import io.alchemystudio.classloader.demo.impl.ProductImpl;

public class UseSimpleClassLoader2 {
    public static void main(String[] args) throws Exception {
        String clazzToPath = ProductImpl.class.getName().replaceAll("\\.", "/") + ".class";
        String path = "target/classes/" + clazzToPath;

        SimpleClassLoader2 cl = new SimpleClassLoader2(path);
        Class clazz = cl.loadClass(ProductImpl.class.getName());
        System.out.println(clazz.getName());
    }
}
