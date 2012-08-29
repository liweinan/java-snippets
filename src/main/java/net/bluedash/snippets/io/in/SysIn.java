package net.bluedash.snippets.io.in;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * mvn -q exec:java -Dexec.mainClass="net.bluedash.snippets.io.in.SysIn"
 * try input 123456789
 * If you input CR, it still counts in data
 */
public class SysIn {
    public static void main(String[] args) throws IOException {
        byte[] data = new byte[10];

        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) System.in.read();
            System.out.write(data[i]);
            System.out.flush();
        }

        System.out.println("====");
        for (byte aData : data) {
            if (aData == '\n') {
                System.out.print("\\n\t");
            } else {
                System.out.printf("%c\t", (char) aData);
            }
        }

        OutputStream out = new FileOutputStream("sysin.dat");
        out.write(data);
        out.close();
    }

}
