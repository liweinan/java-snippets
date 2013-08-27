package net.bluedash.snippets.multicast.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class UDPPoke {
    private int bufferSize;
    private DatagramSocket socket;
    private DatagramPacket packet;

    public UDPPoke(InetAddress host, int port, int bufferSize, int timeout) throws SocketException {
        packet = new DatagramPacket(new byte[1], 1, host, port);
        this.bufferSize = bufferSize;
        socket = new DatagramSocket(0);


    }
}
