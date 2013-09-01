package net.bluedash.snippets.inet;

import java.net.InetAddress;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class InetAddressPlayer {

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("networkaddress.cache.negative.ttl"));
        System.out.println(System.getProperty("networkaddress.cache.ttl"));

        byte[] address = {0xa, 0, 0x1, 0x2}; // 10.0.1.2
        java.net.InetAddress inetAddress = java.net.InetAddress.getByAddress(address);
        System.out.println(inetAddress.getHostAddress());

        java.net.InetAddress oreillyAddress = java.net.InetAddress.getByName("www.oreilly.com");
        System.out.println(oreillyAddress.getHostAddress());

        System.out.println(java.net.InetAddress.getByName(oreillyAddress.getHostAddress()));
        System.out.println(java.net.InetAddress.getByName(oreillyAddress.getHostName()));
    }
}
