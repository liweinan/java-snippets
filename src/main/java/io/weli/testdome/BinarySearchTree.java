package io.weli.testdome;

// https://www.testdome.com/library?page=1&skillArea=30&questionId=77789
public class BinarySearchTree {

    static class Node {
        public int value;
        public Node left, right;

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public static boolean contains(Node root, int value) {
        // Base case: if root is null, value is not found
        if (root == null) {
            return false;
        }

        // If current node's value matches the target, we found it
        if (root.value == value) {
            return true;
        }

        // If value is less than or equal to current node's value, search left subtree
        if (value <= root.value) {
            return contains(root.left, value);
        }
        // If value is greater than current node's value, search right subtree
        else {
            return contains(root.right, value);
        }
    }

    public static void main(String[] args) {
        Node n1 = new Node(1, null, null);
        Node n3 = new Node(3, null, null);
        Node n2 = new Node(2, n1, n3);

        System.out.println(contains(n2, 3)); // Output: true
        System.out.println(contains(n2, 4)); // Output: true
    }
}