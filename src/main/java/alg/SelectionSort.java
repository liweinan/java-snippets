package alg;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class SelectionSort extends BaseSort {

    @Override
    public void sort(Comparable[] comparable) {
        int N = comparable.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(comparable[j], comparable[min]))
                    min = j;
            }
            swap(comparable, i, min);
        }
    }

    public static void main(String[] args) {
        SelectionSort sort = new SelectionSort();
        SortableObject[] data = DataProvider.unsortedObjects();
        DataProvider.printData(data, false);

        sort.sort(data);
        DataProvider.printData(data, false);
        SortHelper helper = new SortHelper(sort);

        System.out.println(helper.isSorted(data));
        helper.printCounter();

        helper.setComparingData(data);
        System.out.println(helper.isSortedFastImpl(0));
        helper.printCounter();
    }
}
