package concurrent.future.current;

import java.util.concurrent.*;

public class EmbeddedTaskFuture<T> implements Future<T> {
    private Callable<T> task;

    public EmbeddedTaskFuture(Callable<T> task) {
        this.task = task;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        try {
            return task.call();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
