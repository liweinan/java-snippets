package io.weli.lc;

public class LC509 {
    static class Solution {
        public int fib(int n) {
            if (n == 0) {
                return 0;
            }
            if (n == 1) {
                return 1;
            }

            int n_1 = 1;
            int n_2 = 0;
            int fib = 0;

            while (n - 2 >= 0) {
                fib = n_1 + n_2;
                n_2 = n_1;
                n_1 = fib;
                n--;
            }

            return fib;
        }
    }

    public static void main(String[] args) {
        var s = new LC509.Solution();
        System.out.println(s.fib(4));
    }
}
