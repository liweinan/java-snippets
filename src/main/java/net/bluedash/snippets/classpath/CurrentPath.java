package net.bluedash.snippets.classpath;

import java.net.URL;
import java.util.Enumeration;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class CurrentPath {

    public static void main(String[] args) throws Exception {

        Enumeration<URL> e = CurrentPath.class.getClassLoader().getResources("");
        while (e.hasMoreElements()) {
            System.out.println("ClassLoader Resource: " + e.nextElement());
        }
        System.out.println("Class Resource: " + CurrentPath.class.getResource("/"));

    }
}
