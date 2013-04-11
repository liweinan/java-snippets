package net.bluedash.snippets.ssl;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Scanner;

public class SSLServer extends Thread {
    private SSLServerSocket serverSocket;

    public SSLServer(SSLServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client accepted.");
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(reader);

                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                while (true) {
                    try {
                        String data = scanner.nextLine();
                        System.out.printf("data: %s\n", data);
                        writer.println(data);
                        writer.flush();
                    } catch (Exception e) {
                        writer.close();
                        socket.close();
                        e.printStackTrace();
                        System.out.println("Client disconnected.");
                        break;
                    }
                }
            }
        } catch (IOException e) {

        }
    }

    private static String PATH = "/Users/weli/projs/java-snippets/src/main/resources/";
    private static String SERVER_KEY_STORE = "demo_ks";
    private static String SERVER_KEY_STORE_PASSWORD = "123123";

    public static void main(String[] args) throws Exception {

        // trust store is different from key store
        // trust store is for verifying incoming certs
        // key store is to provide cert to clients
        // We use same file for trust store and key store for SSLServer
//        System.setProperty("javax.net.ssl.trustStore", PATH + SERVER_KEY_STORE);


        SSLContext context = SSLContext.getInstance("TLS");

        KeyStore ks = KeyStore.getInstance("jceks");
        ks.load(new FileInputStream("/Users/weli/projs/tcprest/src/main/resources/server_ks"), null);
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");
        kf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());

        context.init(kf.getKeyManagers(), null, null);

        ServerSocketFactory factory = context.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(8443);
        serverSocket.setNeedClientAuth(false);

        new SSLServer(serverSocket).start();
    }
}
