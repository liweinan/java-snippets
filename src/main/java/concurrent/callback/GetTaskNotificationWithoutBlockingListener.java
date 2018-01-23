package concurrent.callback;

// https://stackoverflow.com/questions/826212/java-executors-how-to-be-notified-without-blocking-when-a-task-completes

import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class GetTaskNotificationWithoutBlockingListener {

    public static void main(String[] args) throws Exception {
        ExampleService service = new ExampleService();
        GetTaskNotificationWithoutBlockingListener listener = new GetTaskNotificationWithoutBlockingListener();
        CompletableFuture<String> f = CompletableFuture.supplyAsync(service::work);
        f.thenAccept(listener::notify);
        f.join();
        System.out.println("Exiting main()");
    }

    void notify(String msg) {
        System.out.println("Received message: " + msg);
    }
}
