package io.weli.misc;

import java.io.*;

public class FileClass {
    static class XyzTest extends FileClass implements Serializable {
        private String name;

        XyzTest(String name) {
            this.name = name;
            System.out.println("Test");
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) throws Exception {
        XyzTest fileClass = new XyzTest("XYZ");
        FileOutputStream file = new FileOutputStream("Data.txt");
        ObjectOutputStream output = new ObjectOutputStream(file);
        output.writeObject(fileClass);
        output.close();
        FileInputStream fis = new FileInputStream("Data.txt");
        ObjectInputStream is = new ObjectInputStream(fis);
        XyzTest c2 = (XyzTest) is.readObject();
        System.out.println(c2.getName());
        is.close();
    }
}

