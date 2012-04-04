package net.bluedash.snippets.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SimpleClassLoader extends ClassLoader {
    String[] dirs;

    private static final String PREFIX = "net.bluedash.snippets.classloader.";

    public SimpleClassLoader(String path) {
        dirs = path.split(System.getProperty("path.separator"));
    }

    public void extendClasspath(String path) {
        String[] exDirs = path.split(System.getProperty("path.separator"));
        String[] newDirs = new String[dirs.length + exDirs.length];
        System.arraycopy(dirs, 0, newDirs, 0, dirs.length);
        System.arraycopy(exDirs, 0, newDirs, dirs.length, exDirs.length);
        dirs = newDirs;
    }

    public synchronized Class findClass(String name) throws ClassNotFoundException {
        for (String dir : dirs) {
            byte[] buf = getClassData(dir, name);
            if (buf != null)
                return defineClass(PREFIX + name, buf, 0, buf.length);
        }
        throw new ClassNotFoundException();
    }

    protected byte[] getClassData(String directory, String name) {
        String classFile = directory + "/" + name.replace('.', '/') + ".class";
        File f = (new File(classFile));
        printFullPath(f);
        int classSize
                = (new Long(f.length())).intValue();
        byte[] buf = new byte[classSize];
        try {
            FileInputStream filein = new FileInputStream(classFile);
            classSize = filein.read(buf);
            filein.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return buf;
    }

    protected void printFullPath(File f) {
        System.out.println(f.getAbsolutePath());

    }
}