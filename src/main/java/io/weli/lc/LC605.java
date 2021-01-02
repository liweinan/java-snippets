package io.weli.lc;

import java.lang.reflect.Array;

// https://leetcode-cn.com/problems/can-place-flowers/
public class LC605 {
    static class Solution {
        public boolean canPlaceFlowers(int[] flowerbed, int n) {
            if (n == 0) {
                return true;
            }

            if (flowerbed.length == 0) {
                return false;
            }

            if (flowerbed.length == 1) {
                return flowerbed[0] != 1;
            }

            for (int i = 0; i < flowerbed.length; i++) {
                if (i == 0) {
                    if (flowerbed[i] == 0 && flowerbed[i + 1] == 0) {
                        flowerbed[i] = 1;
                        n--;
                    }
                } else if (i == flowerbed.length - 1) {
                    if (flowerbed[i] == 0 && flowerbed[i - 1] == 0) {
                        flowerbed[i] = 1;
                        n--;
                    }
                } else {
                    if (flowerbed[i] == 0 && flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0) {
                        flowerbed[i] = 1;
                        n--;
                    }
                }

                if (n == 0) {
                    break;
                }
            }

            return n == 0;
        }
    }

    public static void main(String[] args) throws Exception {
        var s = new LC605.Solution();
        int[] arr = {1, 0, 0, 0, 1};
        System.out.println(s.canPlaceFlowers(arr, 1));
        System.out.println(s.canPlaceFlowers(arr, 2));
        int[] arr2 = {1};
        System.out.println(s.canPlaceFlowers(arr2, 0));
    }
}
