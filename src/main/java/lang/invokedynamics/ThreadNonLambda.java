package lang.invokedynamics;

/**
 * Created by weinanli on 09/06/2017.
 */
public class ThreadNonLambda {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };

        r.run();
    }
}
