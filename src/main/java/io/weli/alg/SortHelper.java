package io.weli.alg;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class SortHelper {

    private static long debugCounter = 0;

    private SortInterface sort;

    public SortHelper(SortInterface sort) {
        this.sort = sort;
    }

    public static void clearDebugCounter() {
        debugCounter = 0;
    }


    public static void printCounter() {
        System.out.printf("debug counter: %d\n", debugCounter);
    }

    protected boolean isSorted(Comparable[] comparable) {
        clearDebugCounter();
        for (int i = 0; i < comparable.length; i++) {
            for (int j = i; j < comparable.length; j++) {
                debugCounter++;
                if (sort.less(comparable[j], comparable[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    private Comparable[] comparingData = {};

    public Comparable[] getComparingData() {
        return comparingData;
    }

    public void setComparingData(Comparable[] comparingData) {
        this.comparingData = comparingData;
    }

    // set comparingData before using this fast implementation
    protected boolean isSortedFastImpl(int startPoint) {
        if (startPoint > -1 && comparingData.length > 0) {
            if (startPoint == 0) clearDebugCounter();
            debugCounter++;
            if (startPoint < comparingData.length - 1) {
                if (sort.less(comparingData[startPoint], comparingData[startPoint + 1])) {
                    return isSortedFastImpl(startPoint + 1);
                } else {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }


}
