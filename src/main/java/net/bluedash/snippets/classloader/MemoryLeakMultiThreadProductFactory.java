package net.bluedash.snippets.classloader;

import org.jboss.netty.handler.codec.serialization.WeakReferenceMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: weli
 * Date: 5/24/13
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MemoryLeakMultiThreadProductFactory extends ProductFactory {
    // FIXME memory leak
    // Use weakreference map instead
    static protected Map<Long, Proxy> productProxies = new HashMap<Long, Proxy>(); // in multi-thread environment, we need to store each threads' proxy independently.

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

    public static Product reload(String productClassPath) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        cl = new SimpleClassLoader(PREFIX + productClassPath);
        String binaryName = productClassPath.replace('/', '.') + ".ProductImpl";
        Class productImplClass = cl.loadClass(binaryName);

        Proxy productProxy = productProxies.get(Thread.currentThread().getId());

        if (productProxy == null) {
            return newInstance(productImplClass);
        } else {
            ProductInvocationHandler productInvocationHandler = (ProductInvocationHandler) Proxy.getInvocationHandler(productProxy);
            Product replacement = (Product) productImplClass.newInstance();
            productInvocationHandler.setProductInstance(replacement);
            return (Product) productProxy;
        }
    }
}
