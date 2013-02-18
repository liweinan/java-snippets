package net.bluedash.snippets.io;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * 10 09 2012
 *
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ReaderWriter {
    public static void main(String[] args) throws Exception {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(new BufferedWriter(stringWriter));
        writer.write("Hello");
        writer.write(",");
        writer.write("World!");
        writer.flush();
        System.out.println(stringWriter.toString());

        OutputStream output = new OutputStream()
        {
            private StringBuilder string = new StringBuilder();
            @Override
            public void write(int b) throws IOException {
                this.string.append((char) b );
            }

            public String toString(){
                return this.string.toString();
            }
        };

        Writer zipWriter = new OutputStreamWriter(new GZIPOutputStream(output));

        zipWriter.append("Hello, world!");
        zipWriter.flush();
        zipWriter.close();

        System.out.println("zip: " + output.toString());

    }
}
