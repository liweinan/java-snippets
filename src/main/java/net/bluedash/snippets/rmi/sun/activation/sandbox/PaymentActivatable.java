package net.bluedash.snippets.rmi.sun.activation.sandbox;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public interface PaymentActivatable extends Remote {
    public double calculatePayment(double principal, double annualRate, int terms) throws RemoteException;
}
