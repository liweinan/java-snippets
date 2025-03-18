package io.weli.sandbox;

public class MaxSumArray {

    // 给定一个整数数组，计算长度为m的子数组中的元素和的最大值,需要考虑时间复杂度是否最优。
    //
    //* 例如数组[2,1,3,6,8,1],假如m=3,子数组为[2,1,3], [1,3,6], [3,6,8], [6,8,1],需要输出的最大值为17
    //
    public static int maxSum(int[] nums, int k) {
        int max = 0;

//        for (int i = 0; i < nums.length - k; i++) {
//            int sum = 0;
//            for (int j = 0; j < k; j++) {
//                sum += nums[i + j];
//            }
//            max = Math.max(max, sum);
//        }

        for (int i = 0; i < k; i++) {
            max += nums[i];
        }

        int i = 0;
        for (int j = k; j < nums.length; j++) {
            int sum = max;
            sum -= nums[i];
            sum += nums[j];
            i++;
            max = Math.max(max, sum);
        }

        return max;
    }

    public static void main(String[] args) {
        int[] in = new int[]{2, 1, 3, 6, 8, 1};
        System.out.println(maxSum(in, 2));
    }
}
