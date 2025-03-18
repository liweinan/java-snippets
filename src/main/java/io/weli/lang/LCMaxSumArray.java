package io.weli.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://leetcode.cn/problems/maximum-sum-of-distinct-subarrays-with-length-k/
public class LCMaxSumArray {

    public static long maximumSubarraySum(int[] nums, int k) {
        long maxSum = 0;

        for (int i = 0; i < nums.length - k; i++) {
//            System.out.println("check idx: " + i);
            if (notSame(nums, i, k)) {
                maxSum = Math.max(maxSum, sumUp(nums, i, k));
            }
        }

        return maxSum;
    }

    private static long sumUp(int[] nums, int start, int k) {
        long result = 0;

        for (int i = start; i < start + k; i++) {
//            System.out.print("[" + nums[i] + "]");
            result += nums[i];
        }
//        System.out.println();
//        System.out.println("sumup result: " + result);
        return result;
    }

    private static boolean notSame(int[] nums, int start, int k) {
        List<Integer> lst = new ArrayList<>();

        for (int i = start; i < start + k; i++) {
            lst.add(nums[i]);
        }
//        System.out.println("lst: " + lst);
//        System.out.println("count: " + lst.stream().distinct().count() + " k: " + k);
        return lst.stream().distinct().count() == k;
    }

    public static void main(String[] args) {
//        int[] nums = new int[]{1, 2, 2, 3};
//        Arrays.stream(nums).distinct()
//                .forEach(System.out::println);

        maximumSubarraySum(new int[]{1, 5, 4, 2, 9, 9, 9}, 3);
    }
}
