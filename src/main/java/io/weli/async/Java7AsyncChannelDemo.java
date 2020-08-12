package io.weli.async;

import java.net.SocketOption;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class Java7AsyncChannelDemo {
    // https://www.baeldung.com/java-nio-2-async-channels
    public static void main(String[] args) throws Exception {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(null);
        Future<AsynchronousSocketChannel> future = server.accept();

        System.out.println(server.getLocalAddress().toString());
        System.out.println(server.isOpen());

        for (SocketOption option : server.supportedOptions()) {
            System.out.println(option.toString());
        }

        future.cancel(true);
    }
}
