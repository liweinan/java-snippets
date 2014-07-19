package net.bluedash.snippets.security.setup;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Utils {
    private static String DIGITS = "0123456789ABCDEF";

    public static String toHex(byte[] data, int length) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i != length; i++) {
            int v = data[i] & 0xff;
            buf.append(DIGITS.charAt(v >> 4));
            buf.append(DIGITS.charAt(v & 0xf));

        }
        return buf.toString();
    }

    public static String toHex(byte[] data) {
        return toHex(data, data.length);
    }


    public static void main(String args[]) throws Exception {
        System.out.println(toHex("Hello, world!".getBytes()));
        System.out.println(toHex(DIGITS.getBytes()));
        byte[] bytes = {Integer.valueOf(256).byteValue()}; // overflow, max 255 = 1111 1111
        System.out.println(toHex(bytes));
    }
}
