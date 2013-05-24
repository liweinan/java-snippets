package net.bluedash.snippets.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class ProductFactory {
    static protected ClassLoader cl = null;
    static protected Object productProxy; // we store this because we want to get invocation handler from proxy later

    public static Product newInstance() throws InstantiationException,
            IllegalAccessException {
        Product product = ProductImpl.class.newInstance(); // default implementation
        Product productProxy = ProductInvocationHandler.newInstance(product);
        ProductFactory.productProxy = productProxy;
        return productProxy;
    }

    protected static final String PREFIX = "target/classes/";

    public static Product reload(String productClassPath) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        cl = new SimpleClassLoader(PREFIX + productClassPath);
        String binaryName = productClassPath.replace('/', '.') + ".ProductImpl";
        Class productImplClass = cl.loadClass(binaryName);

        Proxy productProxy = (Proxy) ProductFactory.productProxy;
        if (productProxy != null) {
            ProductInvocationHandler productInvocationHandler = (ProductInvocationHandler) Proxy.getInvocationHandler(productProxy);
            Product replacement = (Product) productImplClass.newInstance();
            productInvocationHandler.setProductInstance(replacement);
            ProductFactory.productProxy = productProxy;
        }
        return (Product) productProxy;
    }
}