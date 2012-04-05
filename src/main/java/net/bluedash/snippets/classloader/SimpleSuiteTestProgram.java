package net.bluedash.snippets.classloader;

import java.lang.reflect.Method;

/*
 * mvn install
 * mvn exec:java -Dexec.mainClass="net.bluedash.snippets.classloader.SimpleSuiteTestProgram"
 */
public class SimpleSuiteTestProgram {
    static Class[] formals = {String[].class};
    static Object[] actuals = {new String[]{""}};

    public static void main(String[] args) {
        try {
            for (int i = 0; ; i++) {
                ClassLoader aClassLoader
                        = new SimpleClassLoader("target/classes/net/bluedash/snippets/classloader");
                Class c = aClassLoader.loadClass("net.bluedash.snippets.classloader.TestCase" + i);
                Method m = null;
                try {
                    m = c.getMethod("main", formals);
                } catch (NoSuchMethodException e) {
                    System.out.println("TestCase" + i
                            + ": no main in test case");
                    break;
                }
                try {
                    m.invoke(null, actuals);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
        	System.out.println("no more tests");
        } 
    }
}
