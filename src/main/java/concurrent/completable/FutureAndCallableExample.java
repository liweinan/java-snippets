package concurrent.completable;

import java.util.concurrent.*;

public class FutureAndCallableExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            // 做一些操作
            return "Hello, Martian!";
        };

        Future<String> future = executorService.submit(callable);

        String result = future.get();

        System.out.println(result);

        // 关闭所有tasks，防止同一个thread group里面的task导致main thread被block住无法退出。
        executorService.shutdown();
    }
}
