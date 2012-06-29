package net.bluedash.snippets.classloader.demos;

/*
 * mvn install
 * mvn -q exec:java -Dexec.mainClass="net.bluedash.snippets.classloader.demos.ClassLoaderName"
 */
public class ClassLoaderName {
    public static void main(String[] args) {
        String classLoaderName = ClassLoader.getSystemClassLoader().getClass().toString();
        System.out.println(classLoaderName);
    }

}
