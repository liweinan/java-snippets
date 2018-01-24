package concurrent;

import java.security.InvalidParameterException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PlayWithCallable {

    static class FactorialTask implements Callable<Integer> {
        int number;

        public FactorialTask(int number) {
            this.number = number;
        }

        public Integer call() throws InvalidParameterException {
            int fact = 1;
            for (int count = number; count > 1; count--) {
                    fact = fact * count;
            }

            return fact;
        }
    }

    public static void main(String[] args) throws Exception {
        FactorialTask task = new FactorialTask(5);

        Future<Integer> future = Executors.newSingleThreadExecutor().submit(task);

        System.out.println(future.get().intValue());
    }
}
