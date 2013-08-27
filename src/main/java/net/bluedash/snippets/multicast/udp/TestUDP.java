package net.bluedash.snippets.multicast.udp;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TestUDP {
    static class UDPClient implements Runnable {
        @Override
        public void run() {
            try {
                // User Datagram Protocol, Src Port: 6666 (6666), Dst Port: cbt (7777)
                DatagramSocket socket = new DatagramSocket(6666);
                System.out.println("Client DatagramSocket Local Port: " + socket.getLocalPort());
                System.out.println("Client DatagramPacket Port: " + socket.getPort());
                byte[] buf = "hello this is UDP Test".getBytes();
                DatagramPacket packet =
                        new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 7777);
                Thread.sleep(5000);
                System.out.println("Client: wakeup");
                System.out.println("Client DatagramPacket Port: " + packet.getPort());
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        UDPClient client = new UDPClient();
        Thread t1 = new Thread(client);
        t1.start();
        UDPServerFunc();
    }

    static void UDPServerFunc() {
        try {
            DatagramSocket socket = new DatagramSocket(7777);
            System.out.println("Server DatagramSocket Local Port: " + socket.getLocalPort());
            System.out.println("Server DatagramSocket Port: " + socket.getPort());
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, 0, buf.length);
            socket.receive(packet);
            System.out.println(packet.getAddress().getHostAddress() + " :: "
                    + new String(packet.getData(), 0, packet.getLength()));
            System.out.println("Server DatagramPacket Port: " + packet.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
