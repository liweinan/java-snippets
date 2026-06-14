package io.weli.hackerrank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class BreakingTheRecords {
    public static List<Integer> breakingRecords(List<Integer> scores) {
        int min = scores.get(0);
        int max = scores.get(0);
        int minCnt = 0;
        int maxCnt = 0;



        for (int score : scores) {
            if (score < min) {
                minCnt++;
                min = score;
            }

            if (score > max) {
                maxCnt++;
                max = score;
            }
        }

        List ret = new ArrayList();

        ret.add(minCnt);
        ret.add(maxCnt);
        return ret;

    }

    public static void main(String[] args) {
        var result = breakingRecords(Arrays.stream("10 5 20 20 4 5 2 25 1".split(" ")).map(Integer::parseInt).collect(toList()));

        System.out.println(result); // [2, 4]

    }
}
