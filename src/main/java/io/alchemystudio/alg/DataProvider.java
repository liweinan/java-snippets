package io.alchemystudio.alg;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class DataProvider {

    public static SortableObject[] unsortedObjects() {
        SortableObject so0 = new SortableObject(0, "H");
        SortableObject so1 = new SortableObject(1, "e");
        SortableObject so2 = new SortableObject(2, "l");
        SortableObject so3 = new SortableObject(3, "l");
        SortableObject so4 = new SortableObject(4, "o");
        SortableObject so5 = new SortableObject(5, ",");
        SortableObject so6 = new SortableObject(6, "w");
        SortableObject so7 = new SortableObject(7, "o");
        SortableObject so8 = new SortableObject(8, "r");
        SortableObject so9 = new SortableObject(9, "l");
        SortableObject so10 = new SortableObject(10, "d");
        SortableObject so11 = new SortableObject(11, "!");

        SortableObject[] _unsortedObjects = {so1, so3, so5, so7, so9, so11, so0, so2, so4, so6, so8, so10};
        return _unsortedObjects;

    }

    public static void printData(SortableObject[] data, boolean isDebug) {

        for (SortableObject so : data) {
            if (isDebug) {
                System.out.printf("<%s,%s>\n", so.getKey(), so.getValue());
            } else {
                System.out.print(so.getValue());
            }
        }
        System.out.println("");
    }
}
