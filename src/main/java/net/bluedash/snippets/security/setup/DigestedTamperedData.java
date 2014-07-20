package net.bluedash.snippets.security.setup;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class DigestedTamperedData {
    public static void main(String[] args) throws Exception {
        BouncyCastleProvider.load();

        SecureRandom random = new SecureRandom();
        IvParameterSpec ivSpec = Common.createCtrIvForAES(1, random);
        Key key = Common.createKeyForAES(256, random);
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
        String input = "Transfer 0000100 to AC 1234-5678";
        MessageDigest hash = MessageDigest.getInstance("SHA-1", "BC");

        System.out.println("input: " + input);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] cipherText = new byte[cipher.getOutputSize(input.length() + hash.getDigestLength())];
        System.out.println("digest length: " + hash.getDigestLength()); // should be 20 bytes

        int cipherTextLength = cipher.update(input.getBytes(), 0, input.length(), cipherText, 0);
        System.out.println("cipher text length without digest: " + cipherTextLength);
        System.out.println("cipher text: " + Utils.toHex(cipherText));
        hash.update(input.getBytes());
        byte[] finalHash = hash.digest();
        cipherTextLength += cipher.doFinal(finalHash, 0, hash.getDigestLength(), cipherText, cipherTextLength);
        System.out.println("final hash: " + Utils.toHex(finalHash));
        System.out.println("cipher text length after digest: " + cipherTextLength); // cipher text + digest
        System.out.println("cipher text: " + Utils.toHex(cipherText));


        // tampering step
        cipherText[9] ^= '0' ^ '9';

        // decryption step
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] tamperedPlainText = cipher.doFinal(cipherText);

        int messageLength = tamperedPlainText.length - hash.getDigestLength();
        System.out.println("tampered plain text: " + Common.toString(tamperedPlainText, messageLength)); // only show messageLength, hash part will be copied to messageHash.
        hash.update(tamperedPlainText, 0, messageLength);

        byte[] messageHash = new byte[hash.getDigestLength()];

        System.arraycopy(tamperedPlainText, messageLength, messageHash, 0, messageHash.length); // messageLength is start pos of hash message
        System.out.println("message hash: " + Utils.toHex(messageHash));

        System.out.println("decryption: " + Common.toString(tamperedPlainText));

        byte[] tamperedHash = hash.digest();
        System.out.println("tampered digest: " + Utils.toHex(tamperedHash));
        System.out.println(MessageDigest.isEqual(tamperedHash, finalHash));
    }
}
