package net.bluedash.snippets.io.in;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * @author Weinan Li
 * @date 08 10 2012
 */
public class RandomInputStream extends InputStream {

    private Random generator = new Random();
    private boolean closed = false;

    public int read() throws IOException {
        checkOpen();
        return Math.abs(Integer.valueOf(generator.nextInt() % 256));
    }

    public int read(byte[] data, int offset, int length) throws IOException {
        checkOpen();
        byte[] temp = new byte[length];
        generator.nextBytes(temp);
        System.arraycopy(temp, 0, data, offset, length);
        return length;
    }

    public int read(byte[] data) throws IOException {
        checkOpen();
        generator.nextBytes(data);
        return data.length;
    }

    public long skip(long bytesToSkip) throws IOException {
        checkOpen();
        return bytesToSkip;
    }

    public void close() {
        this.closed = true;
    }

    private void checkOpen() throws IOException {
        if (closed) throw new IOException();
    }

    public int available() {
        return Integer.MAX_VALUE;
    }

    public static void main(String[] args) throws IOException {
        InputStream in = new RandomInputStream();
        byte[] buf = new byte[16];
        in.read(buf);
        for (byte b : buf) {
            System.out.println(Integer.toHexString(Math.abs(Integer.valueOf(Byte.toString(b)))));
        }
    }
}
