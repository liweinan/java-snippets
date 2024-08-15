package io.weli.test;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CopyOnWriteArrayTest {
    @Test
    public void testRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});
            Iterator<Integer> iterator = numbers.iterator();
            while (iterator.hasNext()) {
                iterator.remove();
            }
        });
    }

    @Test
    public void testOrdinaryRemoveInLoop() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(new Integer[]{1, 2, 3, 4}));
        System.out.println(numbers);

        Iterator<Integer> iterator = numbers.iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        System.out.println(numbers);
    }

}
