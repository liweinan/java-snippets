package net.bluedash.snippets.ssl;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SSLClient {

    private static String CLIENT_KEY_STORE = "/Users/weli/projs/java-snippets/src/main/resources/demo_ks";

    public static void main(String[] args) throws Exception {
        // Set the key store to use for validating the server cert.
        System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);
        System.setProperty("javax.net.debug", "ssl,handshake");

        SSLClient client = new SSLClient();
        SSLSocket s = (SSLSocket) client.clientWithoutCert();
        io(s);
        s.close();

        SSLClient client2 = new SSLClient();
        SSLSocket s2 = (SSLSocket) client2.clientWithoutCert();
        io(s2);
        s2.close();
    }

    private static void io(SSLSocket socket) throws IOException {
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer.println("hello");
        writer.flush();
        System.out.println(reader.readLine());
        System.out.println(socket.getSession());
    }

    private Socket clientWithoutCert() throws Exception {
        SocketFactory sf = SSLSocketFactory.getDefault();
        return sf.createSocket("localhost", 8443);
    }
}
