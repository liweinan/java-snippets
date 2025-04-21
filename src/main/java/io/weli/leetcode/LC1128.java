package io.weli.leetcode;

public class LC1128 {
    static class Solution {
        public int numEquivDominoPairs(int[][] dominoes) {
            int[] num = new int[100]; // 加起来的数字不超过100
            int ret = 0;
            for (int[] domino : dominoes) {
                int val = domino[0] < domino[1] ? domino[0] * 10 + domino[1] : domino[1] * 10 + domino[0];
                ret += num[val]; // 求和：sum(n-1)
                num[val]++;
            }
            return ret;
        }
    }

//    作者：LeetCode-Solution
//    链接：https://leetcode-cn.com/problems/number-of-equivalent-domino-pairs/solution/deng-jie-duo-mi-nuo-gu-pai-dui-de-shu-li-yjlz/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numEquivDominoPairs(new int[][]{{1, 2}, {2, 1}, {3, 4}, {5, 6}, {4, 3}, {1, 2}, {2, 1}}));
//        System.out.println(s.numEquivDominoPairs(new int[][]{{}}));
    }
}
