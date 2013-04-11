package net.bluedash.snippets.generic;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class GenericTest {

    @Test
    public void genericVersusRawTest() {
        List<String> a = new ArrayList<String>();
        List b = new ArrayList();

        Assert.assertEquals(a.getClass(), b.getClass());

        boolean found = false;
        for (Class intf : a.getClass().getInterfaces()) {
            if (intf == List.class)
                found = true;
            System.out.println(intf);
        }

        Assert.assertTrue(found);
        System.out.println(List.class);

    }

    @Test
    public void assignTest() {
        Assert.assertTrue(Collection.class.isAssignableFrom(List.class));
    }
}
