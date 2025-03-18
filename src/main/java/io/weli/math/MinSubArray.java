package io.weli.math;

/*

代码
测试用例
测试用例
测试结果
209. 长度最小的子数组
 */
// https://leetcode.cn/problems/minimum-size-subarray-sum/submissions/612489456/
public class MinSubArray {

    public static void main(String[] args) {
        System.out.println(minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
    }

    public static int minSubArrayLen(int target, int[] nums) {
        if (nums[0] >= target)
            return 1;

        int min = Integer.MAX_VALUE;

        int sum = 0;
        sum += nums[0];

        int i = 0;

        for (int j = 1; j < nums.length; j++) {
            sum += nums[j];
//            System.out.println("sum: " + sum);
//            System.out.println("i: " + i + ", j: " + j);
            while (sum >= target) {
//                System.out.println("found");

                min = Math.min(min, j - i + 1);
                sum -= nums[i];
//                System.out.println("adjust sum: " + sum);
                i++;
            }
        }
        if (Integer.MAX_VALUE == min) {
            return 0;
        } else {
            return min;
        }
    }
}
