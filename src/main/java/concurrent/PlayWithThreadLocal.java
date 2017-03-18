package concurrent;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithThreadLocal {
    public static void main(String[] args) {
        final ThreadLocal<Object> obj = new ThreadLocal<Object>() {
            @Override
            protected Object initialValue() {
                return new Object();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    System.out.println(obj.get());
                }
            }.start();
        }
    }

}
