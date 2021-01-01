package io.weli.lc;

import java.util.Arrays;
import java.util.Comparator;

public class LC435 {

    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }

        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[0]));

        int n = intervals.length;
        int[] f = new int[n];
        Arrays.fill(f, 1);
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                if (intervals[j][1] <= intervals[i][0]) {
                    f[i] = Math.max(f[i], f[j] + 1);
                }
            }
        }
        return n - Arrays.stream(f).max().getAsInt();
    }

//    作者：LeetCode-Solution
//    链接：https://leetcode-cn.com/problems/non-overlapping-intervals/solution/wu-zhong-die-qu-jian-by-leetcode-solutio-cpsb/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    public static void main(String[] args) {

        LC435 lc = new LC435();
        int[][] in = {{1, 2}, {2, 3}};
        System.out.println(lc.eraseOverlapIntervals(in));

        int[][] in2 = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        System.out.println(lc.eraseOverlapIntervals(in2));


    }
}
