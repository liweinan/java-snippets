package io.weli.hackerrank;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://www.hackerrank.com/challenges/compare-the-triplets
// https://github.com/liweinan/deepseek-answers/blob/main/files/hr-compare-the-triplets.md
public class CompareTheTriplets {
    public static List<Integer> compareTriplets(List<Integer> a, List<Integer> b) {
        int arr[] = new int[]{0, 0};

        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) > b.get(i)) {
                arr[0]++;
            } else if (a.get(i) < b.get(i)) {
                arr[1]++;
            }
        }

        List<Integer> list = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.toList());

        return list;
    }

    public static void main(String[] args) throws IOException {
        List<Integer> result = CompareTheTriplets.compareTriplets(List.of(1, 2, 3), List.of(3, 2, 1));
    }
}
