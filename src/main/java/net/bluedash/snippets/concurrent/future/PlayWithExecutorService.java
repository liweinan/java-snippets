package net.bluedash.snippets.concurrent.future;

import org.jgroups.blocks.executor.ExecutionService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithExecutorService {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

    }
}
