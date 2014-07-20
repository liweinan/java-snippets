package net.bluedash.snippets.security.setup;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class StringBytes {
    public static void main(String[] args) {
        String data = "abcdefg";
        byte[] rawBytes = data.getBytes();
        byte[] convertedBytes = stringToBytes(data);

        for (int i = 0; i < rawBytes.length; i++) {
            assert rawBytes[i] == convertedBytes[i];
        }

    }

    public static byte[] stringToBytes(String data) {
        byte[] bytes = new byte[data.length()];
        char[] chars = data.toCharArray();

        for (int i = 0; i != chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }
        return bytes;
    }
}
