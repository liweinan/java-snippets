package net.bluedash.snippets.security.setup;

import java.security.Provider;
import java.security.Security;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ListBCCapabilities {
    public static void main(String[] args) {
        Provider provider = Security.getProvider("BC");

        for (Object entry : provider.keySet()) {
            // this indicates the entry actually refers to another entry
            String _entry = entry.toString();

            if (_entry.startsWith("Alg.Alias.")) {
                _entry = _entry.substring("Alg.Alias.".length());
            }

            String factoryClass = _entry.substring(0, _entry.indexOf('.'));
            String name = _entry.substring(factoryClass.length() + 1);

            System.out.println(factoryClass + ": " + name);
        }
    }
}
