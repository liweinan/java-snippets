package alg;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class SelectionSort extends BaseSort {

    @Override
    public void sort(Comparable[] sortableObjects) {
        int N = sortableObjects.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(sortableObjects[j], sortableObjects[min]))
                    min = j;
            }
            swap(sortableObjects, i, min);
        }
    }

    public static void main(String[] args) {
        SelectionSort sort = new SelectionSort();
        SortableObject[] data = DataProvider.unsortedObjects();
        DataProvider.printSortableObjects(data, false);
        System.out.println("\n");
        sort.sort(data);
        DataProvider.printSortableObjects(data, false);
    }
}
