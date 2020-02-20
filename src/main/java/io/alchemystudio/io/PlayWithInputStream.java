package io.alchemystudio.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by weli on 12/10/15.
 */
public class PlayWithInputStream {
    public static void main(String[] args) {
        InputStream is = null;

        try {
            // new input stream created
            is = new BufferedInputStream(new FileInputStream("/tmp/foo"));

            // read and print characters one by one
            System.out.println("Char : " + (char) is.read());
            System.out.println("Char : " + (char) is.read());
            System.out.println("Char : " + (char) is.read());

            // mark is set on the input stream
            is.mark(0);

            System.out.println("Char : " + (char) is.read());
            System.out.println("Char : " + (char) is.read());

            if (is.markSupported()) {
                // reset invoked if mark() is supported
                is.reset();
                System.out.println("RESET");
                byte[] bytes = new byte[2];
                is.read(bytes);
                System.out.println("bytes: " + (char) bytes[0] + (char) bytes[1]);
                System.out.println("Char : " + (char) is.read());
            }
            

        } catch (Exception e) {

            // if any I/O error occurs
            e.printStackTrace();
        } finally {

            // releases system resources associated with this stream
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
