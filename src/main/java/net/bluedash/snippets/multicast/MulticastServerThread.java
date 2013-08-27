package net.bluedash.snippets.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Date;

public class MulticastServerThread extends QuoteServerThread {
    private long FIVE_SECONDS = 5000;

    public MulticastServerThread() {
        super("MulticastServerThread");
    }

    public void run() {
        while (moreQuotes) {
            try {
                byte[] buf;

                String dString;

                if (qfs == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();

                buf = dString.getBytes();

                InetAddress group = InetAddress.getByName("230.0.0.1");
                DatagramPacket packet = new DatagramPacket(buf, buf.length,
                        group, 4446);

                socket.send(packet);

                // sleep for a while
                try {
                    sleep((long) (Math.random() * FIVE_SECONDS));
                } catch (InterruptedException e) {
                }

            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        socket.close();
    }

}
