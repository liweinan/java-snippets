package io.weli.lc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// https://riptutorial.com/fr/java/example/14749/l-utilisation-de-priorityqueue
public class PriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Arrays.asList(9, 2, 3, 1, 3, 8));
        System.out.println(pq);
        pq.remove();
        System.out.println(pq);
        pq.remove();
        System.out.println(pq);
        pq.remove();
        System.out.println(pq);
        pq.remove();
        System.out.println(pq);
        pq.remove();
        System.out.println(pq);
        pq.remove();
        System.out.println(pq);

        // ------
        System.out.println("------");

        try {
            // Exception in thread "main" java.lang.ClassCastException: [I incompatible with java.lang.Comparable
            PriorityQueue pq2 = new PriorityQueue(Arrays
                    .asList(new int[]{1, 1},
                            new int[]{2, 2},
                            new int[]{3, 1},
                            new int[]{1, 3},
                            new int[]{2, 1},
                            new int[]{1, 2}
                    ));
        } catch (ClassCastException e) {
            System.out.println(e.getCause());
        }

        System.out.println("---");

        // Add Comparator
        //

        PriorityQueue<int[]> pq3 =
                new PriorityQueue<>((p1, p2)
                        -> p1[0] != p2[0] ? p2[0] - p1[0] : p2[1] - p1[1]);
//        作者：LeetCode-Solution
//        链接：https://leetcode-cn.com/problems/sliding-window-maximum/solution/hua-dong-chuang-kou-zui-da-zhi-by-leetco-ki6m/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
        pq3.addAll(Arrays
                .asList(new int[]{42, 3},
                        new int[]{26, 2},
                        new int[]{3, 4},
                        new int[]{3, 5},
                        new int[]{99, 1}
                ));
        pq3.forEach(item -> System.out.println(item[0] + " / " + item[1]));
        System.out.println("----");

        pq3.remove();
        pq3.forEach(item -> System.out.println(item[0] + " / " + item[1]));

        System.out.println("----");

        pq3.remove();
        pq3.forEach(item -> System.out.println(item[0] + " / " + item[1]));

        System.out.println("----");

        pq3.remove();
        pq3.forEach(item -> System.out.println(item[0] + " / " + item[1]));

        System.out.println("----");

        pq3.remove();
        pq3.forEach(item -> System.out.println(item[0] + " / " + item[1]));
    }
}
