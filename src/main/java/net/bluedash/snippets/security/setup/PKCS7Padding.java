package net.bluedash.snippets.security.setup;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PKCS7Padding {

    public static void main(String[] args) throws Exception {
        BouncyCastleProvider.load();

        SecretKey key = new SecretKeySpec(Common.KEY192BITS, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
        System.out.println("input: " + Utils.toHex(Common.DATA24BYTES));

        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherText = new byte[cipher.getOutputSize(Common.DATA24BYTES.length)]; // 24 bytes padded to 32 bytes
        int length = cipher.update(Common.DATA24BYTES, 0, Common.DATA24BYTES.length, cipherText, 0);

        cipher.doFinal(cipherText, length);

        System.out.println("cipher: " + Utils.toHex(cipherText));

        // decryption
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plainText = new byte[cipher.getOutputSize(24)]; // padded to 32 bytes
        length = cipher.update(cipherText, 0, cipherText.length, plainText, 0);
        cipher.doFinal(plainText, length);
        System.out.println("plain: " + Utils.toHex(plainText));
    }
}
