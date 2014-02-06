package net.bluedash.snippets.algorithm;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class BinarySearch {
    private int vectors[];

    public BinarySearch(int[] vectors) {
        this.vectors = vectors;
    }

    public int find(int key) {
        double lo = 0;
        double hi = vectors.length - 1;
        int pos = find(key, lo, hi);
        return pos;
    }

    public int find(int key, double lo, double hi) {
        if (lo < hi) {
            int mid = (int) Math.ceil((lo + hi) / 2);
            if (key > vectors[mid]) {
                return find(key, mid, hi);
            } else if (key < vectors[mid]) {
                return find(key, lo, mid);
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        BinarySearch bs = new BinarySearch(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        int pos = bs.find(13);
        System.out.println(pos);
    }
}
