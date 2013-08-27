package net.bluedash.snippets.multicast.udp;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PortScan {
    public static void main(String[] args) {
        for (int port = 1024; port <= 65535; port++) {
            try {
                DatagramSocket server = new DatagramSocket(port);
                server.close();
            } catch (SocketException e) {
                System.out.print(port + ":");
            }
        }
    }
}
