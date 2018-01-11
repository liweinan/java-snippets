package lang;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by weli on 16/07/2017.
 */
public class EchoServer {

    private static Selector _selector;
    private static ByteBuffer _buffer = ByteBuffer.allocateDirect(1024);

    public static void main(String[] args) throws Exception {
        _selector = Selector.open();

        ServerSocketChannel channel = ServerSocketChannel.open();

        channel.socket().bind(new InetSocketAddress(8080));

        channel.configureBlocking(false);

        channel.register(_selector, SelectionKey.OP_ACCEPT);

        while (true) {
            _selector.select();

            Set keys = _selector.selectedKeys();

            Iterator iterator = keys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) (iterator.next());

                if (key.isAcceptable()) {
                    ServerSocketChannel _channel = (ServerSocketChannel) key.channel();
                    SocketChannel readWriteChannel = _channel.accept();

                    readWriteChannel.configureBlocking(false);
                    readWriteChannel.register(_selector, SelectionKey.OP_READ);

                    _buffer.clear();
                    _buffer.put("Hello, this is EchoServer!\r\n".getBytes());
                    _buffer.flip();

                    readWriteChannel.write(_buffer);
                }

                if (key.isReadable()) {
                    SocketChannel readWriteChannel = (SocketChannel) key.channel();
                    _buffer.clear();

                    int count = 0;

                    while ((count = readWriteChannel.read(_buffer)) > 1024) {
                        _buffer.flip();

                        while(_buffer.hasRemaining()) {
                            readWriteChannel.write(_buffer);
                        }
                    }
                }

                
            }
        }

    }
}
