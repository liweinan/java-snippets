package net.bluedash.snippets.multicast.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class UDPDiscardClient {
    public final static int DEFAULT_PORT = 9;

    public static void main(String[] args) {
        String hostname;
        int port = DEFAULT_PORT;

        if (args.length > 0) {
            hostname = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (Exception e) {

            }
        } else {
            hostname = "localhost";
        }

        try {
            InetAddress target = InetAddress.getByName(hostname);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket socket = new DatagramSocket();
            while (true) {
                String line = userInput.readLine();
                if (line.equals(".")) break;
                byte[] data = line.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, target, port);
                socket.send(packet);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
