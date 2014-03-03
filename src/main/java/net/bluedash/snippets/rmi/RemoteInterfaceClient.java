package net.bluedash.snippets.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class RemoteInterfaceClient {

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", RemoteInterfaceConstants.RMI_PORT);
        RemoteInterface rmi = (RemoteInterface) registry.lookup(RemoteInterfaceConstants.RMI_ID);
        System.out.println(rmi.hello("Wilin!"));
    }
}
