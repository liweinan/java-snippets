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

}
