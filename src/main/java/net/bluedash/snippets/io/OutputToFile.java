package net.bluedash.snippets.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * mvn -q exec:java -Dexec.mainClass="net.bluedash.snippets.io.OutputToFile"
 */
public class OutputToFile {

    public static void main(String[] args) throws IOException {
        OutputStream out = new FileOutputStream("numbers.dat");
        try {
            out.write("1234".getBytes());
        } finally {
            out.close();
        }

    }
}
