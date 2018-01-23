package concurrent.future;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class SquareCalculator {
    private ExecutorService executor = new NoopThreadPoolExecutor(3, 3,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

//    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public Future<Integer> calculate(Integer input) {
        return executor.submit(() -> input * input);
    }

    public static void main(String[] args) throws Exception {
        SquareCalculator calc = new SquareCalculator();
        Future<Integer> future = calc.calculate(10); // 这里会block住main thread，在研究相关的Thread Group

        sleep(1000 * 3);

        calc.getExecutor().shutdown(); // 只有执行了shutdown，main thread才不会block住，需要分析原因。
        // 原因分析完了，例子放在了PlayWithDefaultThreadFactory里面
//
//        while(!future.isDone()) {
//            System.out.println("Calculating...");
//            Thread.sleep(300);
//        }

//        System.out.println(future.isDone());



    }
}
