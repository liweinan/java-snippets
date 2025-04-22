package io.weli.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;

// 添加mcast地址：https://blogs.agilefaqs.com/2009/11/08/enabling-multicast-on-your-macos-unix/
public class MulticastSocketClient {

   final static String INET_ADDR = "224.0.0.3";
   final static int PORT = 8888;

   public static void main(String[] args) {
      try {
         InetAddress group = InetAddress.getByName(INET_ADDR);
         MulticastSocket socket = new MulticastSocket(PORT);
         
         NetworkInterface networkInterface = NetworkInterface.getByInetAddress(
            InetAddress.getLocalHost());
         
         socket.joinGroup(new InetSocketAddress(group, PORT), networkInterface);
         
         byte[] buf = new byte[256];
         DatagramPacket packet = new DatagramPacket(buf, buf.length);
         
         socket.receive(packet);
         String received = new String(packet.getData());
         System.out.println("Received: " + received);
         
         socket.leaveGroup(new InetSocketAddress(group, PORT), networkInterface);
         socket.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}


