package net.bluedash.snippets.rmi.naming;

import java.rmi.RemoteException;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class HelloServer implements Hello {

    @Override
    public String sayHello() throws RemoteException {
            return "Hello, Wilin!";
    }
}
