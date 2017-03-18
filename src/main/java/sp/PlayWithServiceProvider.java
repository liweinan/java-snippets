package sp;

import net.bluedash.snippets.security.setup.BouncyCastleProvider;

import java.util.ServiceLoader;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithServiceProvider {
    public static void main(String[] args) {
        ServiceLoader.loadInstalled(BouncyCastleProvider.class);

    }
}
