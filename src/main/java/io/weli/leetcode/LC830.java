package io.weli.leetcode;

import java.util.ArrayList;
import java.util.List;

public class LC830 {
    static class Solution {
        public List<List<Integer>> largeGroupPositions(String s) {
            var result = new ArrayList<List<Integer>>();
            if (s.length() < 3) {
                return result;
            }

            int sum = 1;
            char[] arr = s.toCharArray();

            int idx = 0;

            var item = new ArrayList<Integer>();

            for (int i = 1; i <= arr.length; i++) {
                if (i < arr.length) {
                    if (arr[i] == arr[i - 1]) {
                        sum++;
                    } else {
                        if (sum >= 3) {
                            item.add(idx);
                            item.add(i - 1);
                            result.add(item);
                        }

                        item = new ArrayList<>();
                        sum = 1;
                        idx = i;
                    }
                } else {
                    if (sum >= 3) {
                        item.add(idx);
                        item.add(i - 1);
                        result.add(item);
                    }
                }
            }


            return result;
        }
    }

    public static void main(String[] args) {
        var s = new LC830.Solution();
//        System.out.println(s.largeGroupPositions("Hello, world!"));
        System.out.println(s.largeGroupPositions("abcdddeeeeaabbbcd"));
        System.out.println(s.largeGroupPositions("abbxxxxzzy"));
        System.out.println(s.largeGroupPositions("aaa"));

    }
}
