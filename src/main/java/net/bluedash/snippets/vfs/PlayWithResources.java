package net.bluedash.snippets.vfs;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithResources {
    public static void main(String[] args) throws Exception {
        System.out.println("1 " + PlayWithResources.class.getResource("/"));
        System.out.println("2 " + PlayWithResources.class.getResource(""));
        System.out.println("---");
        System.out.println("a " + PlayWithResources.class.getClass().getResource("/"));
        System.out.println("b " + PlayWithResources.class.getClass().getResource(""));
        System.out.println("---");
        System.out.println("j " + PlayWithResources.class.getClassLoader().getResource("/"));
        System.out.println("k " + PlayWithResources.class.getClassLoader().getResource(""));
        System.out.println("---");
        System.out.println("x " + ClassLoader.getSystemClassLoader().getResource("/"));
        System.out.println("y " + ClassLoader.getSystemClassLoader().getResource(""));
        System.out.println("---");
        System.out.println(PlayWithResources.class);
        System.out.println(PlayWithResources.class.getClass());
        System.out.println(PlayWithResources.class.getClass().getClass());

    }

    public static URL[] search(ClassLoader cl, String prefix, String suffix) throws IOException {
        Enumeration[] e = new Enumeration[]{
                cl.getResources(prefix),
                cl.getResources(prefix + "MANIFEST.MF")
        };

        Set all = new LinkedHashSet();
        URL url;
        URLConnection conn;
        JarFile jarFile;

        for (int i = 0, s = e.length; i < s; ++i) {

        }
        return null;
    }


}
