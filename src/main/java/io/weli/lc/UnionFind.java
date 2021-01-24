package io.weli.lc;

public class UnionFind {
    private int count;
    private final int[] parent;

    public int getCount() {
        return count;
    }

    public UnionFind(int n) {
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
        UnionFind uf = new UnionFind(5);
        uf.union(1, 2);

    }
}
