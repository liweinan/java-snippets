package concurrent.blocking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayWithBlockingQueue {
    public static void main(String[] args) throws Exception {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(10);
        blockingQueue.add("Hello");

//        blockingQueue.take();
//        blockingQueue.take();

        Thread t = new Thread(() -> System.out.println("Hello!"));

        t.start();

        System.out.println("main!");
    }
}
