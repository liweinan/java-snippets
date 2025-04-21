package io.weli.leetcode;

public class LC547 {
    static class Solution {
        static class _UnionFind {
            private int count;
            private final int[] parent;

            public int getCount() {
                return count;
            }

            public _UnionFind(int n) {
                this.count = n;
                parent = new int[n];

                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                }
            }

            public void union(int p, int q) {
                int root_p = find(p);
                int root_q = find(q);

                if (root_p == root_q)
                    return;

                parent[root_p] = root_q;

                count--;
            }

            private int find(int x) {
                while (parent[x] != x) {
                    x = parent[x];
                }
                return x;
            }

            public static void main(String[] args) {
                io.weli.leetcode.UnionFind uf = new io.weli.leetcode.UnionFind(5);
                uf.union(1, 2);

            }
        }

        public int findCircleNum(int[][] isConnected) {
            if (isConnected[0].length == 1) {
                return 1;
            }

//            if (isConnected[0].length == 2) {
//                if (isConnected[0][1] == 1) {
//                    return 1;
//                } else {
//                    return 2;
//                }
//            }

            _UnionFind ud = new _UnionFind(isConnected[0].length);

            for (int i = 0; i < isConnected.length; i++) {
                for (int j = i + 1; j < isConnected[0].length; j++) {
                    if (isConnected[i][j] == 1) {
                        ud.union(i, j);
                    }
                }
            }

            return ud.getCount();
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findCircleNum(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}));
        System.out.println(s.findCircleNum(new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}));
    }
}
