package net.bluedash.snippets.jgroup;

import org.jgroups.JChannel;

/**
 * Created with IntelliJ IDEA.
 * User: weli
 * Date: 8/2/13
 * Time: 2:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlayJGroup {
    public static void main(String[] args) throws Exception {
        JChannel jChannel = new JChannel();
        jChannel.connect("play-channel");
    }
}
