package net.bluedash.snippets.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class RemoteInterfaceImpl extends UnicastRemoteObject implements RemoteInterface {

    protected RemoteInterfaceImpl() throws RemoteException {
        super();
    }

    @Override
    public String hello(String name) throws RemoteException {
        return "Hello, " + name;
    }
}
