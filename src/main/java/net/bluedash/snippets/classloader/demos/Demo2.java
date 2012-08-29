package net.bluedash.snippets.classloader.demos;

import net.bluedash.snippets.classloader.SimpleClassLoader;


/*
 * mvn install
 * mvn -q exec:java -Dexec.mainClass="net.bluedash.snippets.classloader.demos.Demo2"
 */
public class Demo2 {
    public static void main(String args[]) throws Exception {
        ClassLoader cl = new SimpleClassLoader("target/classes/net/bluedash/snippets/classloader/impl2");
        Class clazz = cl.loadClass("net.bluedash.snippets.classloader.impl2.ProductImpl");
    }

}
