package io.weli.lc;

import java.lang.reflect.Array;

// https://leetcode-cn.com/problems/longest-continuous-increasing-subsequence/
public class LC674 {

    static class Solution {
        public int findLengthOfLCIS(int[] nums) {
            if (nums.length == 0) {
                return 0;
            }
            int len = 1;
            int max = 1;

            for (int i = 1; i < nums.length; i++) {
                if (nums[i] > nums[i - 1]) {
                    len++;
                } else {
                    max = Math.max(len, max);
                    len = 1;
                }
            }

            max = Math.max(len, max);

            return max;
        }
    }

    public static void main(String[] args) throws Exception {
        var s = new Solution();
        System.out.println(s.findLengthOfLCIS(new int[]{1, 3, 5, 4, 7}));
        System.out.println(s.findLengthOfLCIS(new int[]{1, 3, 5, 7}));
        System.out.println(s.findLengthOfLCIS(new int[]{}));
        System.out.println(s.findLengthOfLCIS(new int[]{2, 2, 2, 2}));
    }
}
