package io.weli.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private ServerSocket server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader reader;

    public void start(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while (!line.equals("Over")) {
                try {
                    line = reader.readLine();
                    System.out.println(line);
                    out.writeUTF("Server received: " + line);
                } catch (Exception e) {
                    System.out.println(e);
                    break;
                }
            }
            System.out.println("Closing connection");

            socket.close();
            in.close();
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        EchoServer server = new EchoServer();
        server.start(5000);
    }
}
