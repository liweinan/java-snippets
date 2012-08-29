package net.bluedash.snippets.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
//        try {
//            this.serverSocket.setSoTimeout(10000);
//        } catch (SocketException e) {
//            System.out.println(e);
//            return;
//        }

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
                        System.out.println(e.getClass().toString());
                        System.out.println("Client disconnected.");
                        break;
                    }
                }
            }
        } catch (IOException e) {

        }

    }

    public static void main(String[] args) throws IOException {
        new Server(new ServerSocket(Port.DEFAULT)).start();
    }
}
