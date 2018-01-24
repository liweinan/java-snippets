package concurrent;

import java.util.concurrent.*;

public class CalcAsync {

    private ExecutorService executor;

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public Future<String> calcAsync() throws InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();

        executor = Executors.newCachedThreadPool();

        executor.submit(() -> {
            Thread.sleep(3000);
            future.complete("Hello");
            Thread.currentThread().wait(); // 让main thread block住。
            return null;
        });

        return future;
    }

    public static void main(String[] args) throws Exception {
        CalcAsync async = new CalcAsync();
        Future future = async.calcAsync();
        System.out.println(future.get());
        async.getExecutor().shutdown(); // 关掉task，让main thread可以退出。
    }
}
