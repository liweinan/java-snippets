package net.bluedash.snippets.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
* mvn install
* mvn -q exec:java -Dexec.mainClass="net.bluedash.snippets.classloader.ReflectionVsCasting"
*/
public class ReflectionVsCasting {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        ClassLoader aClassLoader
                = new SimpleClassLoader("target/classes/net/bluedash/snippets/classloader");

        Class clazz = aClassLoader.loadClass("net.bluedash.snippets.classloader.Red");
        Method m = clazz.getMethod("show");
        m.invoke(clazz.newInstance());

        try {
            clazz = aClassLoader.loadClass("net.bluedash.snippets.classloader.Red");
            Red c = (Red) clazz.newInstance(); // produces exception
        } catch (RuntimeException e) {
            System.out.println(e);
        }

    }
}
