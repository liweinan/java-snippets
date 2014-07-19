package net.bluedash.snippets.security.setup;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PKCS7Padding {

    public static void main(String[] args)  throws Exception {
        BouncyCastleProvider.start();

        SecretKey key = new SecretKeySpec(Common.KEY192BITS, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
        System.out.println("input: " + Utils.toHex(Common.DATA24BYTES));


    }
}
