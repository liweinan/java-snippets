package net.bluedash.snippets.concurrent.future;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class DummyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Run Rabit Run!");
    }
}
