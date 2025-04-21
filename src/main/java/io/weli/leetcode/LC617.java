package io.weli.leetcode;

import java.util.LinkedList;
import java.util.Queue;

// https://leetcode-cn.com/problems/merge-two-binary-trees/
public class LC617 {

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }

        if (t2 == null) {
            return t1;
        }

        TreeNode out = new TreeNode(t1.val + t2.val);

        out.left = mergeTrees(t1.left, t2.left);
        out.right = mergeTrees(t1.right, t2.right);

        return out;
    }

    public static void main(String[] args) throws Exception {

        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);

        t1.left = new TreeNode(3);
        t1.left.left = new TreeNode(5);
        t1.right = new TreeNode(2);

        t2.left = new TreeNode(1);
        t2.left.right = new TreeNode(4);
        t2.right = new TreeNode(3);
        t2.right.right = new TreeNode(7);

        LC617 solution = new LC617();
        TreeNode t3 = solution.mergeTrees(t1, t2);
        dump(t3);

    }

    static void dump(TreeNode t) {
        if (t == null) {
            return;
        }


        System.out.print(t.val + " ");

        Queue<TreeNode> q = new LinkedList<>();

        q.offer(t);

        while (!q.isEmpty()) {
            TreeNode each_t = q.poll();
            if (each_t.left != null) {
                System.out.print(each_t.left.val + " ");
                q.offer(each_t.left);
            }

            if (each_t.right != null) {
                System.out.print(each_t.right.val + " ");
                q.offer(each_t.right);
            }
        }
    }
}
