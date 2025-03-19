package io.weli.sandbox;

import java.util.Arrays;

// https://leetcode.cn/problems/palindromic-substrings/description/
/*

代码
测试用例
测试结果
测试结果
647. 回文子串
已解答
中等
相关标签
相关企业
提示
给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。

回文字符串 是正着读和倒过来读一样的字符串。

子字符串 是字符串中的由连续字符组成的一个序列。
 */
// https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0647.%E5%9B%9E%E6%96%87%E5%AD%90%E4%B8%B2.md
// https://leetcode.cn/problems/palindromic-substrings/
public class CountSubstring {
    public static void main(String[] args) {

    }

    public int countSubstrings(String s) {
        char[] chars = s.toCharArray();

        boolean[][] dp = new boolean[chars.length][chars.length];

        for (int i = 0; i < chars.length; i++) {
            Arrays.fill(dp[i], false);
        }

        int cnt = 0;

        for (int i = chars.length - 1; i >= 0; i--) { // 左侧
            for (int j = i; j < chars.length; j++) { // 右侧
                if (chars[i] == chars[j]) {
                    if (j - i <= 1) {
                        cnt++;
                        dp[i][j] = true;
                    } else if (dp[i + 1][j - 1]) { // 中间区间
                        cnt++;
                        dp[i][j] = true;
                    }
                }
            }
        }

        return cnt;
    }
}

