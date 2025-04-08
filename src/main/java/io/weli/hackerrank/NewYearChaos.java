package io.weli.hackerrank;

import java.util.Arrays;
import java.util.List;

// https://www.hackerrank.com/challenges/one-month-preparation-kit-new-year-chaos
// https://github.com/liweinan/deepseek-answers/blob/main/files/new-year-chaos.md
public class NewYearChaos {
    public static void minimumBribes(List<Integer> q) {
        int bribes = 0;

        for (int i = q.size() - 1; i >= 0; i--) {
            // Check if current person has moved more than 2 positions forward
            if (q.get(i) - (i + 1) > 2) {
                System.out.println("Too chaotic");
                return;
            }

            // Count how many people bribed the current person
            // We only need to check from one position ahead of original position to current position
            int start = Math.max(0, q.get(i) - 2);
            for (int j = start; j < i; j++) {
                if (q.get(j) > q.get(i)) {
                    bribes++;
                }
            }
        }

        System.out.println(bribes);
    }

    public static void main(String[] args) {
        // Test cases
//        List<Integer> queue1 = Arrays.asList(2, 1, 5, 3, 4);
//        minimumBribes(queue1);  // Output: 3
//
//        List<Integer> queue2 = Arrays.asList(2, 5, 1, 3, 4);
//        minimumBribes(queue2);  // Output: Too chaotic
//
//        List<Integer> queue3 = Arrays.asList(5, 1, 2, 3, 7, 8, 6, 4);
//        minimumBribes(queue3);  // Output: Too chaotic

        List<Integer> queue4 = Arrays.asList(1, 2, 5, 3, 7, 8, 6, 4);
        minimumBribes(queue4);  // Output: 7
    }
}
