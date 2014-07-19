package net.bluedash.snippets.security.setup;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class BouncyCastleProviderTest {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider()); // manually install it, so the program will always show 'BC is installed.'
        // to install it, put the jar into /Library/Java/JavaVirtualMachines/jdk1.7.0_25.jdk/Contents/Home/jre/lib/ext

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
