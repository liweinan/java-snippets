package net.bluedash.snippets.security.setup;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Common {

    public static byte[] KEY192BITS = new byte[]{
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, // 8 * 8 = 64 bits
            0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x18
    }; // 64 * 3 = 192 bits
    // Key length must be 128/192/256 bits.


    // 16 bytes
    public static byte[] DATA16BYTES = new byte[]{
            0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77,
            (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb,
            (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff}; // data must be block aligned
    // DES and Blowfish, for example, have a block size of 8 bytes.
    // AES, the latest addition to the family, has a block size of 16 bytes.

    // 24 bytes
    public static byte[] DATA24BYTES = new byte[]{
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, // 8 bytes
            0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17
    }; // 8 * 3 = 24 bytes
    // not block aligned, needs padding

    public static IvParameterSpec createCtrIvForAES(int messageNumber, SecureRandom random) {
        byte[] ivBytes = new byte[16];
        random.nextBytes(ivBytes);

        ivBytes[0] = (byte) (messageNumber >> 24);
        ivBytes[1] = (byte) (messageNumber >> 16);
        ivBytes[2] = (byte) (messageNumber >> 8);
        ivBytes[3] = (byte) (messageNumber >> 0);

        for (int i = 0; i != 7; i++) {
            ivBytes[8 + i] = 0;
        }

        ivBytes[15] = 1;

        for (int i = 0; i < 16; i++) {
            System.out.println(ivBytes[i]);
        }

        return new IvParameterSpec(ivBytes);
    }

    public static SecretKey createKeyForAES(int bitLength, SecureRandom random) throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
        generator.init(256, random);
        return generator.generateKey();
    }

    public static String toString(byte[] bytes) {
        return toString(bytes, bytes.length);
    }

    /**
     * Convert a byte array of 8 bit char into a String
     * @param bytes the array containing the characters
     * @param length the number of bytes to process
     * @return a String representation of bytes
     */
    public static String toString(byte[] bytes, int length) {
        char[] chars = new char[length];
        for (int i = 0; i != chars.length; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        createCtrIvForAES(1, random);

        System.out.println("0 ^ 9: " + ('0' ^ '9')); // convert ASCII to number
        System.out.println("0 ^ 8: " + ('0' ^ '8'));
    }

}
