package net.bluedash.snippets.security.setup;

import javax.crypto.Cipher;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PrecedenceTest {
    public static void main(String[] args) throws Exception {
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
        System.out.println(cipher.getProvider());

        cipher = Cipher.getInstance("Blowfish/ECB/NoPadding", "BC");
        System.out.println(cipher.getProvider());
    }
}
