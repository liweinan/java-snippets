package net.bluedash.snippets.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithFuture {
    public static void main(String[] args) {
        FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
            public String call() {
                return "Hello, world!";
            }
        });
    }
}
