package net.bluedash.snippets.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", Port.DEFAULT);
        s.setKeepAlive(true);

        PrintWriter writer = new PrintWriter(s.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

        writer.println("hello");
        writer.flush();
        System.out.println(reader.readLine());

        Thread.sleep(1000000000);

        writer.println("world");
        writer.flush();
        System.out.println(reader.readLine());

        s.close();
    }

}
