package io.weli.leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;

// https://riptutorial.com/fr/java/example/14749/l-utilisation-de-priorityqueue
@SuppressWarnings("unchecked")
public class PriorityQueueDemo {
    public static void main(String[] args) {
        // Create a priority queue with initial elements
        PriorityQueue<Integer> pq = new PriorityQueue<>(Arrays.asList(9, 2, 3, 1, 3, 8));
        System.out.println("Initial queue: " + pq);

        // Remove and print elements
        while (!pq.isEmpty()) {
            System.out.println("Removed: " + pq.poll());
            System.out.println("Current queue: " + pq);
        }

        // Test with custom comparator
        PriorityQueue<int[]> pq3 = new PriorityQueue<>((p1, p2) -> 
            p1[0] != p2[0] ? p2[0] - p1[0] : p2[1] - p1[1]);
        
        pq3.addAll(Arrays.asList(
            new int[]{42, 3},
            new int[]{26, 2},
            new int[]{3, 4},
            new int[]{3, 5},
            new int[]{99, 1}
        ));

        // Print elements
        while (!pq3.isEmpty()) {
            int[] item = pq3.poll();
            System.out.println(item[0] + " / " + item[1]);
        }
    }
}
