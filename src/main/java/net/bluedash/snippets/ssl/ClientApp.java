package net.bluedash.snippets.ssl;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Weinan Li
 * @created_at 08 27 2012
 */
public class ClientApp extends Thread {
    private int id = 0;

    PrintWriter writer = null;
    private Socket socket = null;
    BufferedReader reader = null;
    FileOutputStream fileOut = null;

    public ClientApp(int id) {
        this.id = id;
    }

    public void run() {
        try {
            getSocket();
            sendRequest();
            processRequest();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (reader != null)
                    reader.close();
                SocketPool.returnSocket(socket);
            } catch (Exception e) {
                SocketPool.returnSocket(socket);
                e.printStackTrace();
            }

            if (writer != null)
                writer.close();

        }
    }

    private void getSocket() throws Exception {
        boolean fire = true;
        while (fire) {
            try {
                socket = SocketPool.getSocket();
                fire = false;
            } catch (ConcurrentModificationException e) {
                SocketPool.enqueue(Thread.currentThread());
                synchronized (Thread.currentThread()) {
                    Thread.currentThread().wait(10);
                }
            }
        }
    }

    private void sendRequest() throws Exception {
        writer = new PrintWriter(socket.getOutputStream());
        writer.println("<xcatrequest><command>tabdump</command><type>cli</type><becomeuser><username>root</username><password>letmein</password></becomeuser></xcatrequest>");
        writer.flush();
    }


    StringBuilder stringBuilder = new StringBuilder();

    private void processRequest() throws Exception {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String b;

//        while ((b = reader.readLine()) != null) {
//            stringBuilder.append(b).append("\n");
//        }

        stringBuilder.append(reader.readLine()).append("\n");
        reader.close();
        SocketPool.returnSocket(socket);

        new Thread() {
            public void run() {
                try {
                    fileOut = new FileOutputStream(new File("conf/" + id + ".log"));
                    fileOut.write(stringBuilder.toString().getBytes());
                } catch (Exception e) {
                }
            }
        }.start();
    }

    public static class SocketPool {
        private static final LinkedList<Socket> socketPool = new LinkedList<Socket>();
        private static final ArrayList<Socket> usedSocketPool = new ArrayList<Socket>();
        private static final ConcurrentLinkedQueue<Thread> workerQueue = new ConcurrentLinkedQueue<Thread>();

        private static String CLIENT_KEY_STORE = "/Users/weli/projs/tcprest/src/main/resources/client_ks";
        private static String HOST = "localhost";
        private static int PORT = 8443;
        private static int limit = 3;

        public static void enqueue(Thread t) {
            workerQueue.offer(t);
        }

        public static void dequeue() {
            Thread worker = workerQueue.poll();
            if (worker == null)
                return;
            synchronized (worker) {
                worker.notify();
            }
        }

        public static Socket getSocket() throws Exception {
            Socket sc;
            synchronized (SocketPool.class) {
                if (usedSocketPool.size() == limit)
                    throw new ConcurrentModificationException(); // no more socket in pool.
                try {
                    sc = socketPool.pop();
                    if (sc.isClosed())
                        throw new NoSuchElementException();
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
                    socketPool.push(sc);
                    dequeue(); // wakeup a thread in queue
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

        public static Socket createSocket() throws Exception {
            System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);
            Socket sc = SSLSocketFactory.getDefault().createSocket(HOST, PORT);
            usedSocketPool.add(sc);
            return sc;
        }

    }

    public static void main(String[] args) throws Exception {
        List<ClientApp> threads = new ArrayList<ClientApp>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 64; i++) {
            ClientApp client = new ClientApp(i);
            client.start();
            threads.add(client);
        }

        for (ClientApp client : threads) {
            client.join();
        }

        SocketPool.reset();

        System.out.println("***Time consumed: " + (System.currentTimeMillis() - start));
    }


}