package net.bluedash.snippets.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithComparator {

    public static void main(String[] args) {
        Integer[] values = {213, 16, 2058, 54, 10, 1965, 57, 9};
        Arrays.sort(values);
        printResult(values);

        lineBreak();

        Arrays.sort(values, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1; // because of little endian?
            }
        });
        printResult(values);

        lineBreak();

        System.out.format("%d -> %d", values[0], values[1]);

    }

    public static void lineBreak() {
        System.out.print("\n---\n");
    }

    public static void printResult(Integer[] values) {
        for (Integer i : values) {
            System.out.format("%s -> ", i);
        }
    }
}
