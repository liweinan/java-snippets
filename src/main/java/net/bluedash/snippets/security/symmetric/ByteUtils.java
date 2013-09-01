package net.bluedash.snippets.security.symmetric;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ByteUtils {
    private static ByteBuffer buffer = ByteBuffer.allocate(8);

    public static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getLong();
    }
}