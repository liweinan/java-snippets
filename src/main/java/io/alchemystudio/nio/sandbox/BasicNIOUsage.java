package io.alchemystudio.nio.sandbox;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by weinanli on 13/07/2017.
 */
public class BasicNIOUsage {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(8080));

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (!Thread.interrupted()) {
            selector.select();
            Set keys = selector.selectedKeys();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) (iterator.next());
                if (key.isAcceptable()) {
                    dispatchAccept(key);
                }
                iterator.remove();
            }
        }
    }

    static void dispatchAccept(SelectionKey key) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        try {
            System.out.println("Client request accepted");
            serverSocketChannel.accept();
        } catch (IOException e) {
            // nothing
        }
    }
}
