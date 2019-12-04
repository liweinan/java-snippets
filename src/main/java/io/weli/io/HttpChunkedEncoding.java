package io.weli.io;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Random;

// https://alvinalexander.com/java/jwarehouse/openjdk-8/jdk/test/java/net/URLConnection/ChunkedEncoding.java.shtml
public class HttpChunkedEncoding implements Runnable {
    ServerSocket ss;

    /*
     * Our "http" server to return a chunked response
     */
    public void run() {
        try {
            Socket s = ss.accept();

            PrintStream out = new PrintStream(
                    new BufferedOutputStream(
                            s.getOutputStream()));

            /* send the header */
            out.print("HTTP/1.1 200\r\n");
            out.print("Transfer-Encoding: chunked\r\n");
            out.print("Content-Type: text/html\r\n");
            out.print("\r\n");
            out.flush();

            /* delay the server before first chunk */
            Thread.sleep(5000);

            /*
             * Our response will be of random length
             * but > 32k
             */
            Random rand = new Random();

            int len;
            do {
                len = rand.nextInt(128 * 1024);
            } while (len < 32 * 1024);

            /*
             * Our chunk size will be 2-32k
             */
            int chunkSize;
            do {
                chunkSize = rand.nextInt(len / 3);
            } while (chunkSize < 2 * 1024);

            /*
             * Generate random content and check sum it
             */
            byte buf[] = new byte[len];
            int cs = 0;
            for (int i = 0; i < len; i++) {
                buf[i] = (byte) ('a' + rand.nextInt(26));
                cs = (cs + buf[i]) % 65536;
            }

            /*
             * Stream the chunks to the client
             */
            int remaining = len;
            int pos = 0;
            while (remaining > 0) {
                int size = Math.min(remaining, chunkSize);
                out.print(Integer.toHexString(size));
                out.print("\r\n");
                out.write(buf, pos, size);
                pos += size;
                remaining -= size;
                out.print("\r\n");
                out.flush();
            }

            /* send EOF chunk */
            out.print("0\r\n");
            out.flush();

            /*
             * Send trailer with checksum
             */
            String trailer = "Checksum:" + cs + "\r\n";
            out.print(trailer);
            out.print("\r\n");
            out.flush();

            s.close();
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    HttpChunkedEncoding() throws Exception {

        /* start the server */
        ss = new ServerSocket(0);
        (new Thread(this)).start();

        /* establish http connection to server */
        String uri = "http://localhost:" +
                Integer.toString(ss.getLocalPort()) +
                "/foo";
        System.out.println(":::" + uri);
        URL url = new URL(uri);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        /*
         * Server should only send headers if TE:trailers
         * specified - see updated HTTP 1.1 spec.
         */
        http.setRequestProperty("TE", "trailers");

        /* Time how long the getInputStream takes */
        long ts = System.currentTimeMillis();
        InputStream in = http.getInputStream();
        long te = System.currentTimeMillis();

        /*
         * If getInputStream takes >2 seconds it probably means
         * that the implementation is waiting for the chunks to
         * arrive.
         */
        if ((te - ts) > 2000) {
            throw new Exception("getInputStream didn't return immediately");
        }

        /*
         * Read the stream and checksum it as it arrives
         */
        int nread;
        int cs = 0;
        byte b[] = new byte[1024];
        do {
            nread = in.read(b);
            if (nread > 0) {
                for (int i = 0; i < nread; i++) {
                    cs = (cs + b[i]) % 65536;
                }
            }
        } while (nread > 0);

        /*
         * Verify that the checksums match
         */
        String trailer = http.getHeaderField("Checksum");
        if (trailer == null) {
            throw new Exception("Checksum trailer missing from response");
        }
        int rcvd_cs = Integer.parseInt(trailer);
        if (rcvd_cs != cs) {
            throw new Exception("Trailer checksum doesn't equal calculated checksum");
        }

        http.disconnect();

    }

    public static void main(String args[]) throws Exception {
        new HttpChunkedEncoding();
    }
}
