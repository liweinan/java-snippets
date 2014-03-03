package net.bluedash.snippets.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class RemoteInterfaceServer {
    public static final void main(String[] args) throws Exception {
        RemoteInterfaceImpl impl = new RemoteInterfaceImpl();
        Registry registry = LocateRegistry.createRegistry(RemoteInterfaceConstants.RMI_PORT);
        registry.bind(RemoteInterfaceConstants.RMI_ID, impl);
        System.out.println("RMI Server started.");
    }
}
