package net.bluedash.snippets.classloader;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ProductFactory {
    static private ClassLoader cl = null;
    static private Class implClass = ProductImpl.class; // default class
    static private List instances = new ArrayList();

    public static Product newInstance() throws InstantiationException,
            IllegalAccessException {
        Product obj = (Product) implClass.newInstance();
        Product anAProxy = (Product) ProductIH.newInstance(obj);
        instances.add(new WeakReference(anAProxy));
        return anAProxy;
    }

    private static final String PREFIX = "target/classes/";

    public static void reload(String dir) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        cl = new SimpleClassLoader(PREFIX + dir);
        String binaryName = dir.replace('/', '.') + ".ProductImpl";
        implClass = cl.loadClass(binaryName);

        List newInstances = new ArrayList();

        for (int i = 0; i < instances.size(); i++) {
            Proxy x = (Proxy) ((WeakReference) instances.get(i)).get();
            if (x != null) {
                ProductIH aih = (ProductIH) Proxy.getInvocationHandler(x);
                Object oldObject = aih.getTarget();
                Object replacement = implClass.newInstance();
                aih.setTarget(replacement);
                newInstances.add(new WeakReference(x));
            }
        }

        instances = newInstances;
    }
}