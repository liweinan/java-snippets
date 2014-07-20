package net.bluedash.snippets.security.setup;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class TamperedData {
    public static void main(String[] args) throws Exception {
        BouncyCastleProvider.load();

        SecureRandom random = new SecureRandom();
        IvParameterSpec ivSpec = Common.createCtrIvForAES(1, random);
        Key key = Common.createKeyForAES(256, random);
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
        String input = "Transfer 0000100 to AC 1234-5678";

        System.out.println("input: " + input);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] cipherText = cipher.doFinal(input.getBytes());

        // tampering step
        cipherText[9] ^= '0' ^ '9';

        // decryption step
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] plainText = cipher.doFinal(cipherText);

        System.out.println("decryption: " + Common.toString(plainText));
    }
}
