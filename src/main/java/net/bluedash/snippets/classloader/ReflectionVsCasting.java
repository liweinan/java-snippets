package net.bluedash.snippets.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
* mvn install
* mvn -q exec:java -Dexec.mainClass="net.bluedash.snippets.classloader.ReflectionVsCasting"
*/
public class ReflectionVsCasting {

    public static void main(String[] args) {
        ClassLoader aClassLoader
                = new SimpleClassLoader("target/classes/net/bluedash/snippets/classloader");
        try {
            Class clazz = aClassLoader.loadClass("net.bluedash.snippets.classloader.Red");
            Method m = clazz.getMethod("show");
            m.invoke(clazz.newInstance());
            // This will produce java.lang.reflect.InvocationTargetException:
            // Color c = (Color) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
