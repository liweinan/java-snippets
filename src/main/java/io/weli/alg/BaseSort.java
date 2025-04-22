package io.weli.alg;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class BaseSort<T extends Comparable<T>> {
    protected abstract void sort(T[] arr);

    protected boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    protected void exch(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    protected boolean isSorted(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (less(arr[i], arr[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Integer[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        BaseSort<Integer> sorter = new BaseSort<Integer>() {
            @Override
            protected void sort(Integer[] arr) {
                Arrays.sort(arr);
            }
        };
        sorter.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}