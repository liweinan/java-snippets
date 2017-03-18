package concurrent;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class TestThread {

    static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(500);
                Thread t = Thread.currentThread();
                System.out.println("当前线程名字：" + t.getName() + " 当前线程的优先级别为：" + t.getPriority() + " ID:"
                        + t.getId() + " this.id:" + this.getId());

//			System.out.println("当前线程名字：" + this.getName() + " 当前线程的优先级别为：" + this.getPriority() + " ID:"+ this.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyThread mt = new MyThread();
        new Thread(mt, "Name1").start();
        new Thread(mt, "Name2").start();
        new Thread(mt).start();
        System.out.println(Thread.currentThread().getName()); // main主方法
    }
}
