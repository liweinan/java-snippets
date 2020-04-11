package io.alchemystudio.classloader;

public class ClassLoaderName {
    public static void main(String[] args) throws Exception {
        System.out.println(ClassLoaderName.class.getClassLoader().toString());
    }
}
