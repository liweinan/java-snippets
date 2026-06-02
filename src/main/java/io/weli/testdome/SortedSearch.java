package io.weli.testdome;

public class SortedSearch {
    public static int countNumbers(int[] sortedArray, int lessThan) {
        if (sortedArray == null || sortedArray.length == 0) {
            return 0;
        }

        int left = 0;
        int right = sortedArray.length - 1;
        int result = 0;  // 记录最后一个小于 lessThan 的位置

        while (left <= right) {
            int mid = left + (right - left) / 2;  // 避免溢出

            if (sortedArray[mid] < lessThan) {
                // 当前元素小于目标，记录位置，继续向右找
                result = mid + 1;  // mid+1 就是小于的元素个数
                left = mid + 1;
            } else {
                // 当前元素大于等于目标，向左找
                right = mid - 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(SortedSearch.countNumbers(new int[]{1, 3, 5, 7}, 4));
    }
}
