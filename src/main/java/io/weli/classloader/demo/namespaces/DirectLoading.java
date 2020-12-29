package io.weli.classloader.demo.namespaces;

import io.weli.classloader.demo.impl.ProductImpl;

public class DirectLoading {
    public static  void main(String[] args) throws Exception {
        ClassLoader classLoader = DirectLoading.class.getClassLoader();
        Class clazz = classLoader.loadClass(ProductImpl.class.getName());
        System.out.println(clazz);
        ProductImpl impl = (ProductImpl) clazz.getDeclaredConstructor().newInstance();
        System.out.println(impl);
    }
}
