package net.bluedash.snippets.security.setup;

import java.security.Security;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class BouncyCastleProvider {

    public static void start() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
}
