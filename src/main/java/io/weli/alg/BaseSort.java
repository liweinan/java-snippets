package io.weli.alg;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public abstract class BaseSort implements SortInterface {
    public boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    protected void swap(Comparable[] a, int i, int j) {
        Comparable _swap = a[i];
        a[i] = a[j];
        a[j] = _swap;
    }

}