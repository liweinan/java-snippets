package concurrent.future;

import static java.lang.Thread.sleep;

public class PlayWithDefaultThreadFactory {
    public static void main(String[] args) {

        // 这个DefaultThreadFactory从Executors里面抠出来的，
        // 它会把新的thread创建在main thread所属的group里。
        DefaultThreadFactory factory = new DefaultThreadFactory();

        // 同一个thread group里面的thread执行没结束的时候，main thread不会退出，会被block住。
        Thread t = factory.newThread(() -> {
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 这个thread跑起来以后，main thread会继续执行，直到block在结束的地方，
        // 等待t里面的sleep的3秒钟完成，除非像下面这样给interrupt()。
        t.start();

        // 让worker thread直接退出，这样main thread就不会block在结束的位置了
        t.interrupt();
    }
}
