package io.weli.bc;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;
import java.util.Iterator;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class BC {
    public static void main(String[] args) {
        System.out.println(Security.getProvider("BC"));
        Security.addProvider(new BouncyCastleProvider());

        Provider provider = Security.getProvider("BC");

        Iterator iter = provider.keySet().iterator();

        while (iter.hasNext()) {
            String entry = (String) iter.next();

            // this indicates the entry actually refers to another entry
            if (entry.startsWith("Alg.Alias.")) {
                entry = entry.substring("Alg.Alias.".length());
            }

            String factoryClass = entry.substring(0, entry.indexOf('.'));
            String name = entry.substring(factoryClass.length() + 1);

            System.out.println(factoryClass + ": " + name);
        }

    }
}
