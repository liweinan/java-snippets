package io.weli.leetcode;

public class LC724 {
    static class Solution {
        public int pivotIndex(int[] nums) {
            if (nums.length < 3) {
                return -1;
            }

            int idx = 1;
            int left = 0;
            int right;
            int total = 0;

            for (int i = 0; i < nums.length; i++) {
                total += nums[i];
            }

            // 边界条件
            if (total - nums[0] == 0) {
                return 0;
            }

            boolean found = false;
            for (int i = 1; i < nums.length - 1; i++) {
                left += nums[i - 1];
                right = total - left - nums[i];
                if (left == right) {
                    found = true;
                    break;
                }
                idx++;
            }


            if (found) {
                return idx;
            } else if (total == nums[nums.length - 1]) {
                return nums.length - 1;
            } else {
                return -1;
            }
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.pivotIndex(new int[]{1, 7, 3, 6, 5, 6})); // 3
        System.out.println(s.pivotIndex(new int[]{1, 2, 3})); // -1
        System.out.println(s.pivotIndex(new int[]{-1, -1, -1, 0, 1, 1})); // 0
        System.out.println(s.pivotIndex(new int[]{-1, -1, 1, 1, 0, 0})); // 4
        System.out.println(s.pivotIndex(new int[]{-1, -1, 0, 1, 1, -1})); // 0
    }
}
