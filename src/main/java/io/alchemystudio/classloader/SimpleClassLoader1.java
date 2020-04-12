package io.alchemystudio.classloader;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class SimpleClassLoader1 extends ClassLoader {
    String[] dirs;

    public SimpleClassLoader1(String path) {
        dirs = path.split(System.getProperty("path.separator"));
        String[] _dirs = dirs.clone();
        for (String dir : _dirs) {
            extendClasspath(dir);
        }
    }

    public String[] getDirs() {
        return dirs;
    }

    public void extendClasspath(String path) {
        String[] segments = path.split("/");
        String[] exDirs = new String[segments.length];
        for (int i = 0; i < (segments.length); i++) {
            exDirs[i] = popd(segments, i);
        }

        String[] newDirs = new String[dirs.length + exDirs.length];
        System.arraycopy(dirs, 0, newDirs, 0, dirs.length);
        System.arraycopy(exDirs, 0, newDirs, dirs.length, exDirs.length);
        dirs = Stream.of(newDirs)
                .filter(Predicate.not(String::isEmpty))
                .toArray(String[]::new);
    }

    private String popd(String[] pathSegments, int level) {
        StringBuffer path = new StringBuffer();
        for (int i = 0; i < level; i++) {
            path.append(pathSegments[i]).append("/");
        }
        return path.toString();
    }

    public static void main(String[] args) {
        SimpleClassLoader1 cl = new SimpleClassLoader1("/Users/weli/projs/java-snippets/target/classes");
        for (String dir : cl.getDirs()) {
            System.out.println(dir);
        }
    }
}
