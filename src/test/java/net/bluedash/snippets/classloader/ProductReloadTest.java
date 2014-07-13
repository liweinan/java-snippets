package net.bluedash.snippets.classloader;


import junit.framework.Assert;
import org.junit.Test;

public class ProductReloadTest {

    @Test
    public void testProductFactory() throws Exception {
        Product product = ProductFactory.newInstance();
        Assert.assertEquals("ProductImpl", product.getName());

        ProductFactory.reload("net/bluedash/snippets/classloader/impl2");
        /* the product is replaced by a new instance */
        Assert.assertEquals("ProductImpl2", product.getName());
    }

    private static Boolean ERROR = false;

    @Test
    public void testMultiThreadProductFactory() throws Exception {
        Product product = MultiThreadProductFactory.newInstance();
        Assert.assertEquals("ProductImpl", product.getName());

        Thread t1 = createThread("+");
        Thread t2 = createThread(".");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        Assert.assertFalse(ERROR);
        Thread.sleep(100);
        Assert.assertEquals(3, MultiThreadProductFactory.productProxies.size());
    }

    private Thread createThread(final String mark) {
        return new Thread() {
            public void run() {
                try {
                    Product product = MultiThreadProductFactory.newInstance(); // each thread can reload its classes independently
                    for (int i = 0; i < 20; i++) {
                        Thread.sleep(100);
                        System.out.print(mark);
                        if (i / 2 == 0) {
                            MultiThreadProductFactory.reload("net/bluedash/snippets/classloader/impl2/impl3");
                            if (!"ProductImpl3".equals(product.getName())) {
                                synchronized (ERROR) {
                                    System.out.print("X");
                                    ERROR = true;
                                }
                            }
                        } else {
                            MultiThreadProductFactory.reload("net/bluedash/snippets/classloader/impl2");
                            if (!"ProductImpl2".equals(product.getName())) {
                                synchronized (ERROR) {
                                    System.out.print("X");
                                    ERROR = true;
                                }
                            }
                        }

                    }
                } catch (Exception e) {
                    synchronized (ERROR) {
                        System.out.print("E");
                        ERROR = true;
                    }
                }
            }
        };
    }

}
