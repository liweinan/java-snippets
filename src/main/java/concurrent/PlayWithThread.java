package concurrent;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by weli on 06/04/2017.
 */
public class PlayWithThread {

    static class SingleThreadExecutor implements Executor {

        @Override
        public void execute(Runnable r) {
            r.run();
        }
    }

    static class OneThreadPerTaskExecutor implements Executor {

        @Override
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

    public static void main(String[] args) throws Exception {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Random random = new Random(System.currentTimeMillis());
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread ID: " + Thread.currentThread().getId());
            }
        };

//        Executor executor = new SingleThreadExecutor();
//        Executor executor = new OneThreadPerTaskExecutor();
        Executor executor = Executors.newSingleThreadExecutor();
//        Executor executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 3; i++) {
            executor.execute(r);
        }
    }

}
