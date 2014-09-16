package net.bluedash.snippets.rmi.naming;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public interface Hello extends Remote {

    public String sayHello() throws RemoteException;
}
