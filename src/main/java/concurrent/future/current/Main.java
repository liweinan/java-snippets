package concurrent.future.current;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws Exception {
        // 一个最基础的的ExecutorService和Future。
        // 这个CurrentThreadExecutor内部不维护任何线程池，只是简单地把一个任务封装成进这个EmbeddedTaskFuture。
        // 这个EmbeddedTaskFuture只是包含一个task，然后它的get()方法会去除法所包含Callable任务的call()方法。
        // 虽然这个实现很简陋，没有任何多线程的任务执行与管理能力，但是它可以帮助我们了解各个接口的设计理念。
        ExecutorService executor = new CurrentThreadExecutor();
        Future future = executor.submit(() -> "Hello, world!");
        System.out.println("Result: " + future.get());
    }
}
