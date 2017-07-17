package lang.invokedynamics;

/**
 * Created by weinanli on 09/06/2017.
 */
public class ThreadLambda {
    public static void main(String[] args) {
        Runnable r = () -> System.out.println("Hello");
        r.run();
    }
}
