package io.weli.alg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://leetcode.cn/problems/two-sum/
// https://www.baeldung.com/java-convert-string-list-characters
public class LCTwoSum {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);

        map.put(nums[0], 0);

//        System.out.println(map);

        for (int i = 1; i < nums.length; i++) {
//            System.out.println(map);
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            } else {
                map.put(nums[i], i);
            }
        }

        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        int[] result = twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(result.length);
        System.out.println(result[0]);
        System.out.println(result[1]);
        Arrays.stream(result).map(i -> i + 1).forEach(System.out::println);
    }
}
