package net.bluedash.snippets.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Bag<T> {
    private static List store = new ArrayList();

    public void add(T item) {
        store.add(item);
    }

    public boolean isEmpty() {
        return store.isEmpty();
    }

    public int size() {
        return store.size();
    }


}
