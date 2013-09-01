package net.bluedash.snippets.security.setup;

import java.security.Provider;
import java.security.Security;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class BouncyCastleProviderTest {
    public static void main(String[] args) {
//        Security.addProvider(new BouncyCastleProvider());

        String providerName = "BC";
        for (Provider provider : Security.getProviders()) {
            System.out.println(provider.getName());
        }

        if (Security.getProvider(providerName) == null) {
            System.out.println(providerName + " provider not installed");
        } else {
            System.out.println(providerName + " is installed.");
        }
    }
}
