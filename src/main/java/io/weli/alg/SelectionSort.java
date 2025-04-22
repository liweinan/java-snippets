package io.weli.alg;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SelectionSort<T extends Comparable<T>> extends BaseSort<T> {

    @Override
    protected void sort(T[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(arr[j], arr[min])) {
                    min = j;
                }
            }
            if (min != i) {
                exch(arr, i, min);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {64, 34, 25, 12, 22, 11, 90};
        SelectionSort<Integer> sorter = new SelectionSort<>();
        sorter.sort(arr);
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
