package nio.sandbox;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by weinanli on 14/07/2017.
 */
public class EchoServer {

    private static Selector _selector;
    private static ByteBuffer _buffer = ByteBuffer.allocateDirect(1024);

    public static void main(String[] args) throws Exception {
        _selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(8080));

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(_selector, SelectionKey.OP_ACCEPT);

        while (!Thread.interrupted()) {
            _selector.select();
            Set keys = _selector.selectedKeys();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) (iterator.next());

                if (key.isAcceptable()) {
                    acceptRequestAndRegisterReadEvent(key);
                }

                if (key.isReadable()) {
                    readRequestAndEcho(key);

                }
                iterator.remove();
            }
        }
    }

    private static void readRequestAndEcho(SelectionKey key) throws IOException {
        SocketChannel readWriteChannel = (SocketChannel) key.channel();
        _buffer.clear();

        int count = 0;
        while ((count = readWriteChannel.read(_buffer)) > 0) {
            _buffer.flip();

            while(_buffer.hasRemaining()) {
                readWriteChannel.write(_buffer);
            }
        }

        _buffer.clear();
    }

    private static void acceptRequestAndRegisterReadEvent(SelectionKey key) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel readWriteChannel = serverSocketChannel.accept();
            
            readWriteChannel.configureBlocking(false);
            readWriteChannel.register(_selector, SelectionKey.OP_READ);

            _buffer.clear();
            _buffer.put("Hi!\r\n".getBytes());
            _buffer.flip();
            readWriteChannel.write(_buffer);
            
        } catch (IOException e) {
            // nothing to do
        }
    }
}
