package net.bluedash.snippets.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public interface RemoteInterface extends Remote {
    public String hello(String name) throws RemoteException;
}
