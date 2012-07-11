package net.bluedash.snippets.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by IntelliJ IDEA.
 * User: weli
 * Date: 7/12/12
 * Time: 2:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class NullOutputStream extends OutputStream {
    private boolean closed = false;

    public void write(int b) throws IOException {
        if (closed) throw new IOException("Write to closed stream");
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        if (data == null) throw new NullPointerException("data is null");
        if (closed) throw new IOException("Write to closed stream");
    }

    public void close() {
        closed = true;
    }

    public static void main(String[] args) throws IOException {
        NullOutputStream out = new NullOutputStream();
        out.write("Sink".getBytes());

        PrintStream ps = new PrintStream(out);
        System.setOut(ps); // redirect standard out to null
        System.setErr(ps); // redirect standard error to null
    }
}
