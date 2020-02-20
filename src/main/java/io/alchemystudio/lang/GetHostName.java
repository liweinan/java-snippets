package io.alchemystudio.lang;

import java.net.InetAddress;

public class GetHostName {
    public static void main(String[] args) throws Exception {

        System.out.println(InetAddress.getLocalHost().getHostName());
    }
}
