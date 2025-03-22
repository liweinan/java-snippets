package io.weli.misc;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

public class SSC {
ServerSocketChannel channel;
    public SSC() throws IOException {
//        this.channel = new ServerSocketChannel(80);
        this.channel = ServerSocketChannel.open();

    }
}
