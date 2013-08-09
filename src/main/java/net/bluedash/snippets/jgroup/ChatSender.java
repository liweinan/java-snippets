package net.bluedash.snippets.jgroup;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: weli
 * Date: 8/2/13
 * Time: 2:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChatSender {
    private JChannel channel;

    private void start() throws Exception {
        channel = new JChannel();
        channel.connect("ChatCluster");
        eventLoop();
        channel.close();
    }

    private void eventLoop() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.print("> ");
                System.out.flush();
                String line = in.readLine().toLowerCase();
                if (line.startsWith("quit") || line.startsWith("exit")) {
                    break;
                }
                Message msg = new Message(null, null, line);
                channel.send(msg);
            } catch (Exception e) {
            }
        }
    }


    public static void main(String[] args) throws Exception {
        ChatSender play = new ChatSender();
        play.start();
    }
}
