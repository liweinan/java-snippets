package io.weli.commons;

public class SystemClassPaths {
    public static void main(String[] args) {
        for (String path : System.getProperty("java.class.path").split(":")) {
            System.out.println(path);
        }
    }
}
