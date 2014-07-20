package net.bluedash.snippets.security.setup;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ECBMode {
    public static void main(String[] args) throws Exception {
        BouncyCastleProvider.load();

        byte[] input = new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07
        };

        byte[] keyBytes = new byte[]{
                0x01, 0x23, 0x45, 0x67,
                (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
        };

        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS7Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
        int cipherTextLength = cipher.update(input, 0, input.length, cipherText, 0);
        cipherTextLength += cipher.doFinal(cipherText, cipherTextLength);

        System.out.println("out: " + Utils.toHex(cipherText));
    }
}
