package io.alchemystudio.ssl;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

// https://learning.oreilly.com/videos/hands-on-cryptography-with/9781838554972/9781838554972-video3_2
public class GeneratorKeyPair {
    public static void main(String[] args) throws Exception {
        final KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
        g.initialize(2048);
        final KeyPair kp = g.generateKeyPair();

        final PublicKey publicKey = kp.getPublic();
        final PrivateKey privateKey = kp.getPrivate();

        System.out.println(publicKey.toString());
        System.out.println(privateKey.toString());
    }
}
