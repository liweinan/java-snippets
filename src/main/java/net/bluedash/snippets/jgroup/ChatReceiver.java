package net.bluedash.snippets.jgroup;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * Created with IntelliJ IDEA.
 * User: weli
 * Date: 8/10/13
 * Time: 1:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChatReceiver extends ReceiverAdapter {
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": " + msg.getObject());
    }

    public void start() throws Exception {
        JChannel channel = new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster");
    }

    public static void main(String[] args) throws Exception {
        new ChatReceiver().start();
    }
}