package net.bluedash.snippets.algorithm.trie;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Trie {

    private static final int R = 256; // ascii

    private static class Node {
        private int val;
        private Node[] leaves = new Node[R];
        private Node parent;

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public Node[] getLeaves() {
            return leaves;
        }

        public void setLeaves(Node[] leaves) {
            this.leaves = leaves;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }


    }

    private Node root = new Node();

    public void put(String key, int val) {
        Node currentNode = root;

        int keyLength = key.length();
        for (int i = 0; i < keyLength; i++) {

            Node node = currentNode.getLeaves()[key.charAt(i)];

            if (node == null) {
                node = new Node();
                currentNode.getLeaves()[key.charAt(i)] = node;
            }
            currentNode = node;
        }
        currentNode.setVal(val);
    }

    //recursive version
    public void put2(String key, int val) {
        _put(root, key, 0, val);
    }


    private void _put(Node current, String key, int idx, int val) {
        if (idx == key.length()) {
            current.val = val;
            return;
        }

        if (current.getLeaves()[key.charAt(idx)] == null) {
            Node next = new Node();
            current.getLeaves()[key.charAt(idx)] = next;
        }

        _put(current.getLeaves()[key.charAt(idx)], key, idx + 1, val);
    }

    public Node get(String key) {
        int keyLength = key.length();

        Node currentNode = root;

        for (int i = 0; i < keyLength; i++) {
            Node next = currentNode.getLeaves()[key.charAt(i)];
            if (next == null)
                return null;
            currentNode = next;
        }

        return currentNode;
    }


    public static void main(String[] args) throws Exception {
        Node root = new Node();

        // put abc = 0
        Node a = new Node();
        root.getLeaves()['a'] = a;

        Node b = new Node();
        a.getLeaves()['b'] = b;

        Node c = new Node();
        c.setVal(0);
        b.getLeaves()['c'] = c;

        // get value of abc
        System.out.println(
                root.getLeaves()['a'].getLeaves()['b'].getLeaves()['c'].getVal());

        // now let's use put method to achieve above goal
        Trie trie = new Trie();
        trie.put("abc", 0);
        System.out.println(trie.get("abc").getVal());

        trie.put("abcd", 1);
        System.out.println(trie.get("abcd").getVal());

        trie.put2("aba", 2);
        System.out.println(trie.get("aba").getVal());

    }


}
