package net.bluedash.snippets.multicast.sandbox;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayGround {

    private static class MulticastSniffer {
        public static void recieve(String groupName, int groupPort) {
            try {
                InetAddress group = InetAddress.getByName(groupName);

                MulticastSocket socket = new MulticastSocket(groupPort);
                socket.joinGroup(group);

                byte[] buffer = new byte[8192];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String s = new String(packet.getData());
                System.out.println("received: " + s);

            } catch (Exception e) {

            } finally {
                // TODO
            }

        }
    }

    private static class MulticastSender {

        public static void send(String groupName, int groupPort) {
            byte ttl = (byte) 1; // local subnet
            MulticastSocket socket = null;
            InetAddress group = null;
            try {
                group = InetAddress.getByName(groupName);

                byte[] data = "1234567890ABCDEF".getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, group, groupPort);

                socket = new MulticastSocket();
                socket.setSoTimeout(1000);

                socket.joinGroup(group);
                socket.send(packet);
            } catch (Exception e) {

            } finally {

                if (socket != null) {
                    try {
                        socket.leaveGroup(group);
                    } catch (Exception e) {
                    }
                    socket.close();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        InetAddress host = InetAddress.getByName("230.0.0.1");
        printNames(host);

        host = InetAddress.getByName("127.0.0.1");
        printNames(host);

        DatagramSocket ephemeralDatagramSocket = new DatagramSocket(0, host);
        System.out.println("port: " + ephemeralDatagramSocket.getPort());
        System.out.println("local port: " + ephemeralDatagramSocket.getLocalPort());

        new Thread() {
            @Override
            public void run() {
                MulticastSniffer.recieve("all-systems.mcast.net", 4000); // 224.0.0.1
            }
        }.start();
        Thread.sleep(1000); // give sniffer 1s to start receiving datagrams
        MulticastSender.send("all-systems.mcast.net", 4000);
    }

    public static void printNames(InetAddress host) {
        System.out.println("hostAddress: " + host.getHostAddress());
        System.out.println("hostName: " + host.getHostName());
        System.out.println("canonicalHostName: " + host.getCanonicalHostName());
    }
}
