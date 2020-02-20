package io.alchemystudio.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        // declaration section:
// declare a server io.weli.socket and a client io.weli.socket for the server
// declare an input and an output stream
        ServerSocket echoServer = null;
        String line;
        DataInputStream is;
        PrintStream os;
        Socket clientSocket = null;
// Try to open a server io.weli.socket on port 9999
// Note that we can't choose a port less than 1023 if we are not
// privileged users (root)
        try {
            echoServer = new ServerSocket(9999);
        } catch (IOException e) {
            System.out.println(e);
        }
// Create a io.weli.socket object from the ServerSocket to listen and setup
// connections.
// Open input and output streams
        try {
            clientSocket = echoServer.accept();
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
// As long as we receive data, echo that data back to the client.
            while (true) {
                line = is.readLine();
                os.println(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
