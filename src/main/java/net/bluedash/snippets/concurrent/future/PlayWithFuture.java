package net.bluedash.snippets.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithFuture {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future work = service.submit(new Callable<String>() {
            public String call() {
                return "Hello, world!";
            }
        });

        System.out.println(work.get());

        if (work.isDone()) {
            service.shutdown();
        }
    }
}
