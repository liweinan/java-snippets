package concurrent.volatiledemo;

/**
 * Created by weli on 6/20/16.
 */
public class NoVolatile {
    boolean waiting = true;

    public void test() {
        new Thread(new Runnable() {
            public void run() {
                while (waiting == true) {
                    // wait
                }

                System.out.println("Thread 1 finished.");
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                // Sleep for a bit so that thread 1 has a chance to start
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Thread 2 shutdown...");
                waiting = false;
            }
        }).start();
    }

    public static void main(String[] args) {

        new NoVolatile().test();
    }

}
