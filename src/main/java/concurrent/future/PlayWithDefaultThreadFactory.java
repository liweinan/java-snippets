package concurrent.future;

import static java.lang.Thread.sleep;

public class PlayWithDefaultThreadFactory {
    public static void main(String[] args) {
        DefaultThreadFactory factory = new DefaultThreadFactory(); // 这个DefaultThreadFactory会把新的thread创建在main thread所属的group里

        // 同一个thread group里面的thread执行没结束的时候，main thread不会退出，会被block住
        Thread t = factory.newThread(() -> {
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start(); // 这个thread跑起来以后，main thread会继续执行，直到block在结束的地方，等待t里面的sleep的3秒钟完成，除非像下面这样给interrupt()。

        t.interrupt(); // 使用这个方法可以让t这个thread迅速退出
    }
}
