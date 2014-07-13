package net.bluedash.snippets.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.WeakHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: weli
 * Date: 5/24/13
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultiThreadProductFactory extends ProductFactory {
    static protected WeakHashMap<Long, Proxy> productProxies = new WeakHashMap<Long, Proxy>(); // in multi-thread environment, we need to store each threads' proxy independently.

    public static Product newInstance() throws InstantiationException,
            IllegalAccessException {
        return newInstance(ProductImpl.class);// default implementation
    }

    public static Product newInstance(Class clazz) throws InstantiationException,
            IllegalAccessException {
        Product product = (Product) clazz.newInstance(); // default implementation
        Product productProxy = ProductInvocationHandler.newInstance(product);
        productProxies.put(Long.valueOf(Thread.currentThread().getId()), (Proxy) productProxy);
        return productProxy;
    }

    public static void reload(String productClassPath) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        cl = new SimpleClassLoader(PREFIX + productClassPath);
        String binaryName = productClassPath.replace('/', '.') + ".ProductImpl";
        Class productImplClass = cl.loadClass(binaryName);

        Proxy productProxy = productProxies.get(Thread.currentThread().getId());

        if (productProxy == null) {
            newInstance(productImplClass);
        } else {
            ProductInvocationHandler productInvocationHandler = (ProductInvocationHandler) Proxy.getInvocationHandler(productProxy);
            Product replacement = (Product) productImplClass.newInstance();
            productInvocationHandler.setProductInstance(replacement);
        }
    }
}
