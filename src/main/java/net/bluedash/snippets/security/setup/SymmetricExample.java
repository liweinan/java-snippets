package net.bluedash.snippets.security.setup;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class SymmetricExample {

    public static void main(String args[]) throws Exception {



        BouncyCastleProvider.load();
        SecretKeySpec keySpec = new SecretKeySpec(Common.KEY192BITS, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");
        System.out.println("input text: " + Utils.toHex(Common.DATA16BYTES));

        byte[] cipherText = new byte[Common.DATA16BYTES.length];

        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        int cipherTextLength = cipher.update(Common.DATA16BYTES, 0, Common.DATA16BYTES.length, cipherText, 0);
        int left = cipher.doFinal(cipherText, cipherTextLength);
        cipherTextLength += left; // doFinal() is very similar to update() in that it may also put out 0 or more bytes,
        // depending on the kind of Cipher object you specified with getInstance().
        System.out.println("left: " + left);
        System.out.println(Common.DATA16BYTES.length == cipherTextLength);

        System.out.println(Utils.toHex(Common.DATA16BYTES));
        System.out.println(Utils.toHex(cipherText));

    }
}
