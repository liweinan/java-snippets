package io.alchemystudio.lang;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;


public class PlayWithCopyOnWriteArrayList {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> numbers
                = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});

        Iterator<Integer> iterator = numbers.iterator();

        numbers.add(10);

        List<Integer> result = new LinkedList<>();
        iterator.forEachRemaining(result::add);

        assertThat(result).containsOnly(1, 3, 5, 8);

        Iterator<Integer> iterator2 = numbers.iterator();
        List<Integer> result2 = new LinkedList<>();

        iterator2.forEachRemaining(result2::add);

        assertThat(result2).containsOnly(1, 3, 5, 8, 10);

    }
}
