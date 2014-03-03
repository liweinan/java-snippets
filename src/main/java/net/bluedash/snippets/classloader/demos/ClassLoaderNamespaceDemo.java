package net.bluedash.snippets.classloader.demos;

import net.bluedash.snippets.classloader.SimpleClassLoader;

import java.io.IOException;


/*
 * mvn install
 * mvn -q exec:rmi -Dexec.mainClass="net.bluedash.snippets.classloader.demos.ClassLoaderNamespaceDemo"
 */
public class ClassLoaderNamespaceDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        ClassLoader cl1 = new SimpleClassLoader("target/classes/net/bluedash/snippets/classloader/demos");
        Class myClass1 = cl1.loadClass("net.bluedash.snippets.classloader.demos.HelloImpl1");
        HelloImpl1 hello = (HelloImpl1) myClass1.newInstance();

        ClassLoader cl2 = new SimpleClassLoader("target/classes/net/bluedash/snippets/classloader/demos");

        Class myClass2 = cl2.loadClass("net.bluedash.snippets.classloader.demos.HelloImpl1");

        System.out.println(myClass1.getClassLoader());
        System.out.println(myClass2.getClassLoader());


        System.out.println(myClass1 == myClass2);



    }
}
