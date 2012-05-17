package net.bluedash.snippets.weakref;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A simple list wich holds only weak references to the original objects.
 *
 * @author Martin Entlicher
 */
public class WeakList extends AbstractList {

    private ArrayList items;

    /**
     * Creates new WeakList
     */
    public WeakList() {
        items = new ArrayList();
    }

    public WeakList(Collection c) {
        items = new ArrayList();
        addAll(0, c);
    }

    public void add(int index, Object element) {
        items.add(index, new WeakReference(element));
    }

    public Iterator iterator() {
        return new WeakListIterator();
    }

    public int size() {
        removeReleased();
        return items.size();
    }

    public java.lang.Object get(int index) {
        return ((WeakReference) items.get(index)).get();
    }

    private void removeReleased() {
        for (Iterator it = items.iterator(); it.hasNext(); ) {
            WeakReference ref = (WeakReference) it.next();
            if (ref.get() == null) {
                items.remove(ref);
            }
        }
    }

    private class WeakListIterator implements Iterator {

        private int n;
        private int i;

        public WeakListIterator() {
            n = size();
            i = 0;
        }

        public boolean hasNext() {
            return i < n;
        }

        public Object next() {
            return get(i++);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
