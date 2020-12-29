package io.weli.lc;

import lombok.Getter;
import lombok.Setter;

public class TreeNode {
    int val;

    @Getter
    @Setter
    TreeNode left;

    @Getter
    @Setter
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }


}