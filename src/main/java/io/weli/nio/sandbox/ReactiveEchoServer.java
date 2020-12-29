package io.weli.nio.sandbox;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weinanli on 14/07/2017.
 */
public class ReactiveEchoServer {

    private static Selector _selector;
    private static ByteBuffer _buffer = ByteBuffer.allocateDirect(1024);

    private static final int WORKER_POOL_SIZE = 10;
    private static ExecutorService _workerPool;

    public static void main(String[] args) throws Exception {

        _workerPool = Executors.newFixedThreadPool(WORKER_POOL_SIZE);

        _selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(8080));

        serverSocketChannel.configureBlocking(false);

        SelectionKey _key = serverSocketChannel.register(_selector, SelectionKey.OP_ACCEPT);
        _key.attach(new Acceptor(_key));

        while (!Thread.interrupted()) {
            _selector.select();
            Set keys = _selector.selectedKeys();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) (iterator.next());
                iterator.remove();

                Runnable acceptorOrHandler = (Runnable) key.attachment();
                if (acceptorOrHandler != null) {
                    acceptorOrHandler.run();
                }
            }
        }
    }

    static private class Acceptor implements Runnable {

        private SelectionKey key;

        public Acceptor(SelectionKey key) {
            this.key = key;
        }

        public void run() {
            try {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new Handler(socketChannel);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    static private class Handler implements Runnable {
        private SocketChannel readWriteChannel;

        public Handler(SocketChannel socketChannel) throws IOException {
            readWriteChannel = socketChannel;
            readWriteChannel.configureBlocking(false);
            SelectionKey _key = readWriteChannel.register(_selector, SelectionKey.OP_READ);
            _key.attach(this);
        }

        public void run() {
            _workerPool.execute(() -> {
                try {
                    process();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        private synchronized void process() throws IOException {
            _buffer.clear();

            int count = 0;
            while ((count = readWriteChannel.read(_buffer)) > 0) {
                _buffer.flip();

                while (_buffer.hasRemaining()) {
                    readWriteChannel.write(_buffer);
                }
            }

            _buffer.clear();

            _selector.wakeup();
        }
    }
}
