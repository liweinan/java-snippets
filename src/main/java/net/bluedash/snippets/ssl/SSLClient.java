package net.bluedash.snippets.ssl;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class SSLClient extends Thread {

    public static void main(String[] args) throws Exception {
        List<SSLClient> threads = new ArrayList<SSLClient>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            SSLClient client = new SSLClient();
            client.start();
            threads.add(client);
        }

        for (SSLClient client : threads) {
            client.join();
        }

        SocketPool.reset();

        System.out.println("***Time consumed: " + (System.currentTimeMillis() - start));
    }

    private Socket getSocket() throws Exception {
        Socket socket = null;
        boolean fire = true;
        while (fire) {
            try {
                socket = SocketPool.getSocket();
                fire = false;
            } catch (ConcurrentModificationException e) {
                //retry
            }
        }
        return socket;
    }

    private PrintWriter writer = null;
    private BufferedReader reader = null;

    public void write(Socket socket) throws Exception {
        writer = new PrintWriter(socket.getOutputStream());
        writer.println("hello");
        writer.flush();
    }

    private String response;

    public void read(Socket socket) throws Exception {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        response = reader.readLine();
        reader.close(); // Very important to close reader before socket is returned to pool.
        SocketPool.returnSocket(socket);
        new Thread() {
            public void run() {
                System.out.println(response);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void run() {

        Socket socket = null;
        try {
            socket = getSocket();

            write(socket);
            read(socket);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null)
                    writer.close();
                SocketPool.returnSocket(socket);
            } catch (Exception e) {
                SocketPool.returnSocket(socket);
                e.printStackTrace();
            }

        }
    }

    private static class SocketPool {
        private static final String CLIENT_KEY_STORE = "/Users/weli/projs/tcprest/src/main/resources/client_ks";

        private static final LinkedList<Socket> socketPool = new LinkedList<Socket>();
        private static final ArrayList<Socket> usedSocketPool = new ArrayList<Socket>();

        private static int limit = 30;

        public static Socket getSocket() throws Exception {
            Socket sc;
            synchronized (SocketPool.class) {
                if (usedSocketPool.size() == limit) {
                    throw new ConcurrentModificationException(); // no more socket in pool.
                }
                try {
                    sc = socketPool.pop();
                    if (sc.isClosed()) {
                        throw new NoSuchElementException();
                    }
                    usedSocketPool.add(sc);
                    return sc;
                } catch (NoSuchElementException e) {
                    return createSocket();
                }

            }
        }

        public static void returnSocket(Socket sc) {
            if (sc != null) {
                synchronized (SocketPool.class) {
                    usedSocketPool.remove(sc);
                    if (!sc.isClosed())
                        socketPool.push(sc);
                }
            }
        }

        public static void reset() {
            try {
                for (Socket sc : socketPool) {
                    sc.close();
                }
                for (Socket sc : usedSocketPool) {
                    sc.close();
                }
            } catch (Exception e) {
            }
        }

        private static Socket createSocket() throws Exception {
            System.setProperty("javax.net.ssl.trustStore", "/Users/weli/projs/tcprest/src/main/resources/client_ks");
            Socket sc = SSLSocketFactory.getDefault().createSocket("localhost", 8443);
            usedSocketPool.add(sc);
            return sc;
        }
    }

}
