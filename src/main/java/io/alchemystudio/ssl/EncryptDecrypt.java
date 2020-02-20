package io.alchemystudio.ssl;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

// https://gist.github.com/destan/b708d11bd4f403506d6d5bb5fe6a82c5
public class EncryptDecrypt {

    private static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x ", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");

        String pubKeyStr = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxSWnxllXurPO0YnD2dBW\n" +
                "lPTzVhWAPJ1m/yloUyiDm3HUd5xrUm3Lb75B0OYfbUkHig4hsfva6AP1HlIMlwIX\n" +
                "FLoRjR80C/fHySELG+uOBv+wbQh/l8UhL4NTM7IUmWCxKsaSWiA/39GH2MVSS8CA\n" +
                "lY4eCVcAadfE9bXY/akEgEvwzJUof1+HLFhQR9ECkLaKfsZMUY2CsZ+g4hRMqrf2\n" +
                "71Hk18ikMGjk2JP1KD4JibWep8ZEzpbN7AuztY9DE4BsV4S3Q2/ncvmUufHGd2QE\n" +
                "QEUug22o04sU1WhLnl+bNDsLNzHU+dK31ddXYNKZpywauQcA1qaHAFVRGy8OlU9n\n" +
                "swIDAQAB\n" +
                "-----END PUBLIC KEY-----"
                        .replaceAll("-----END PUBLIC KEY-----", "");

        pubKeyStr = pubKeyStr.replaceAll("-----BEGIN PUBLIC KEY-----\n", "");
        pubKeyStr = pubKeyStr.replaceAll("\n", "");

        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("<> pubKeyStr");
        System.out.println(pubKeyStr);
        System.out.println("\n\n\n");

        byte[] decoded = Base64.getDecoder().decode(pubKeyStr);

        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("<> DECODED-PUBLIC-KEY");
//        for (byte b : decoded) {
//            System.out.print(b + " ");
//        }
        System.out.println(bytesToHex(decoded));
        System.out.println("\n\n\n");

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(decoded);

        PublicKey pubKey = factory.generatePublic(keySpecX509);
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("<> PublicKey");
//        for (byte b : pubKey.getEncoded()) {
//            System.out.print(b + " ");
//        }
        System.out.println(bytesToHex(pubKey.getEncoded()));
        System.out.println("\n\n\n");

        String str = "Hello, world!";

        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("<> str: " + str);
        System.out.print("<> ");
//        for (byte b : str.getBytes()) {
//            System.out.print(b + " ");
//        }
        System.out.println(bytesToHex(str.getBytes()));
        System.out.println("\n\n\n");

        testAlg(pubKey, "RSA/ECB/OAEPWithSHA-1AndMGF1Padding ", str);
        testAlg(pubKey, "RSA/ECB/OAEPwithSHA1andMGF1Padding", str);
        testAlg(pubKey, "RSA/ECB/OAEPwithSHA-224andMGF1Padding", str);
        testAlg(pubKey, "RSA/ECB/OAEPwithSHA-256andMGF1Padding", str);
        testAlg(pubKey, "RSA/ECB/OAEPwithSHA-384andMGF1Padding", str);
        testAlg(pubKey, "RSA/ECB/OAEPwithSHA-512andMGF1Padding", str);


    }

    private static void testAlg(PublicKey pubKey, String alg, String msg) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] out = cipher.doFinal(msg.getBytes());

        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("<> Encrypted: " + msg);
        System.out.println("<> Alg: " + alg);
        System.out.print("<> ");
//        for (byte o : out) {
//            System.out.print(o + " ");
//        }
        System.out.println(bytesToHex(out));
        System.out.println("\n\n\n");
    }
}
