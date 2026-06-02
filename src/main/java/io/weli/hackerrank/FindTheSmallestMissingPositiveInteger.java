package io.weli.hackerrank;

public class FindTheSmallestMissingPositiveInteger {
    public static int firstMissingPositive(int[] nums) {
        int n = nums.length;

        // 将每个数字放到正确的位置
        for (int i = 0; i < n; i++) {
            // 当前数字在 [1, n] 范围内，且不在正确位置
            while (nums[i] >= 1 && nums[i] <= n && nums[i] != nums[nums[i] - 1]) {
                // 交换到正确位置
                int targetIdx = nums[i] - 1;
                int temp = nums[i];
                nums[i] = nums[targetIdx];
                nums[targetIdx] = temp;
            }
        }

        // 扫描找到第一个缺失的正整数
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        // 所有位置都正确，说明数组包含 1 到 n，答案是 n+1
        return n + 1;
    }

    public static void main(String[] args) {
        int[] nums1 = {3, 4, -1, 1};
        System.out.println(firstMissingPositive(nums1));  // 2

        int[] nums2 = {1, 2, 0};
        System.out.println(firstMissingPositive(nums2));  // 3

        int[] nums3 = {7, 8, 9, 11, 12};
        System.out.println(firstMissingPositive(nums3));  // 1
    }
}
