package io.alchemystudio.classloader;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SimpleClassLoader2 extends SimpleClassLoader1 {


    public SimpleClassLoader2(String path) {
        super(path);
    }

    public synchronized Class findClass(String name)
            throws ClassNotFoundException {
        for (String dir : dirs) {
            byte[] buf = getClassData(dir, name);
            if (buf != null)
                return defineClass(name, buf, 0, buf.length);
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
        try {
            FileInputStream filein = new FileInputStream(classFile);
            filein.read(buf);
            filein.close();
        } catch (IOException e) {
            return null;
        }
        return buf;
    }

}
