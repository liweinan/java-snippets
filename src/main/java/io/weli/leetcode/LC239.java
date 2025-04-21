package io.weli.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LC239 {
    static class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length;
            PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
                public int compare(int[] pair1, int[] pair2) {
                    return pair1[0] != pair2[0] ? pair2[0] - pair1[0] : pair2[1] - pair1[1];
                }
            });
            for (int i = 0; i < k; ++i) {
                pq.offer(new int[]{nums[i], i});
            }
            int[] ans = new int[n - k + 1];
            ans[0] = pq.peek()[0];
            for (int i = k; i < n; ++i) {
                pq.offer(new int[]{nums[i], i});
                while (pq.peek()[1] <= i - k) {
                    pq.poll();
                }
                ans[i - k + 1] = pq.peek()[0];
            }
            return ans;
        }
    }


//    作者：LeetCode-Solution
//    链接：https://leetcode-cn.com/problems/sliding-window-maximum/solution/hua-dong-chuang-kou-zui-da-zhi-by-leetco-ki6m/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    public static void main(String[] args) {
//        List<Integer> l = new ArrayList<>();
//        l.add(1);
//        l.add(2);
//        l.add(3);
//
//        for (var i : l) {
//            if (i == 2) {
//                break;
//            }
//            System.out.println(i);
//        }


        var s = new LC239.Solution();

//        int[] arr1 = {1, 3, -1, -3, 5, 3, 6, 7};
//        s.maxSlidingWindow(arr1, 3);
//
//        int[] arr2 = {1};
//        s.maxSlidingWindow(arr2, 3);

        int[] arr3 = {1, -1};
        s.maxSlidingWindow(arr3, 1);
    }
}
