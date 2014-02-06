package net.bluedash.snippets.concurrent;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class RegularBooleanMRSWRegister implements Register<Boolean> {
    ThreadLocal<Boolean> last;

    boolean registerValue;

    public RegularBooleanMRSWRegister(int capacity) {
        last = new ThreadLocal<Boolean>() {
            protected Boolean initialValue() {
                return false;
            }
        };
    }

    @Override
    public Boolean read() {
        return registerValue;
    }

    @Override
    public void write(Boolean v) {
        System.out.println("last: " + last.get());
        if (v != last.get()) {
            last.set(v);
            registerValue = v;
        }
    }

    public static void main(String[] args) throws Exception {
        final RegularBooleanMRSWRegister register = new RegularBooleanMRSWRegister(10);

        /* Can't be multiple Write */
        Thread t1 = new Thread() {
            public void run() {
                System.out.println("t1: w(T)");
                register.write(true);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1: w(T)");
                register.write(true);
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2: w(F)");
                register.write(false);
            }
        };
        Thread t3 = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("t3: " + register.read());
                    Thread.sleep(2000);
                    System.out.println("t3: " + register.read());
                    Thread.sleep(2000);
                    System.out.println("t3: " + register.read());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // t1: w(T)       w(T)
        // t2:      w(F)
        // t3:                  r(?)
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
    }
}
