package io.alchemystudio.classloader;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SimpleClassLoader2 extends SimpleClassLoader1 {

    public SimpleClassLoader2(String path) {
        super(path);
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        try {
            return findClass(className);
        } catch (ClassNotFoundException e) {
            return super.loadClass(className);
        }
    }

    public synchronized Class findClass(String name)
            throws ClassNotFoundException {
        System.out.println("findClass -> " + name);
        for (String dir : dirs) {
            byte[] buf = getClassData(dir, name);
            if (buf != null) {
                System.out.println("Loaded '" + name + "' from: " + dir);
                return defineClass(name, buf, 0, buf.length);
            }
        }
        throw new ClassNotFoundException();
    }

    protected byte[] getClassData(String directory, String name) {
        String[] tokens = name.split("\\.");
        String classFile = directory + "/" + tokens[tokens.length - 1]
                + ".class";
        File f = (new File(classFile));
        int classSize = (Long.valueOf(f.length())).intValue();
        byte[] buf = new byte[classSize];
        try (FileInputStream is = new FileInputStream(classFile)) {
            is.read(buf);
        } catch (IOException e) {
            return null;
        }
        return buf;
    }

}
