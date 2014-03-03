package net.bluedash.snippets.classloader;

import junit.framework.Assert;
import net.bluedash.snippets.classloader.SimpleClassLoader;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class SimpleClassLoaderSmokeTest {

    private static String PATH = "/Users/weli/projs/rmi-snippets/target/classes";
    private static final String PREFIX = "target/classes/";

    @Test
    public void testExtendClasspath() {
        SimpleClassLoader cl = new SimpleClassLoader(PATH);
        for (String dir : cl.getDirs()) {
            System.out.println(dir);
        }

        assertEquals(cl.getDirs().length,  PATH.split("/").length + 1);

    }

    @Test
    public void testMultipleLevelClassLoading() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ClassLoader cl = new SimpleClassLoader("target/classes/net/bluedash/snippets/classloader/impl2");
        Class clazz = cl.loadClass("net.bluedash.snippets.classloader.impl2.ProductImpl");
        assertNotNull(clazz);
        Method method = clazz.getMethod("getName");
        Assert.assertEquals("ProductImpl2", method.invoke(clazz.newInstance(), null));
    }
}
