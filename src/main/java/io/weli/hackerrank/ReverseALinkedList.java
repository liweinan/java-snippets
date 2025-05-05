package io.weli.hackerrank;

// https://www.hackerrank.com/challenges/one-month-preparation-kit-reverse-a-linked-list
public class ReverseALinkedList {
    // Node structure for the singly linked list
    class SinglyLinkedListNode {
        public int data;
        public SinglyLinkedListNode next;

        public SinglyLinkedListNode(int nodeData) {
            this.data = nodeData;
            this.next = null;
        }
    }

    // Singly linked list class
    class SinglyLinkedList {
        public SinglyLinkedListNode head;
        public SinglyLinkedListNode tail;

        // Constructor
        public SinglyLinkedList() {
            this.head = null;
            this.tail = null;
        }

        // Insert a node at the end of the list
        public void insertNode(int nodeData) {
            SinglyLinkedListNode node = new SinglyLinkedListNode(nodeData);

            if (this.head == null) {
                // If list is empty, set head and tail to new node
                this.head = node;
                this.tail = node;
            } else {
                // Append node and update tail
                this.tail.next = node;
                this.tail = node;
            }
        }

        // Reverse the linked list (best iterative approach)
        public SinglyLinkedListNode reverse() {
            // Handle empty list or single node
            if (this.head == null || this.head.next == null) {
                return this.head;
            }

            SinglyLinkedListNode prev = null;
            SinglyLinkedListNode curr = this.head;

            while (curr != null) {
                // Store next node
                SinglyLinkedListNode nextNode = curr.next;
                // Reverse the link
                curr.next = prev;
                // Move prev and curr one step forward
                prev = curr;
                curr = nextNode;
            }

            // Update head to the new head (last node)
            this.head = prev;
            // Update tail to the original head (now last node)
            this.tail = curr == null ? prev : curr;
            return this.head;
        }

        // Utility to print the list (for testing)
        public void printList() {
            SinglyLinkedListNode current = this.head;
            while (current != null) {
                System.out.print(current.data);
                if (current.next != null) {
                    System.out.print(" ");
                }
                current = current.next;
            }
            System.out.println();
        }
    }

    // Main function to test the implementation with a predefined list
    public static void main(String[] args) {
        // Create a new linked list
        SinglyLinkedList llist = new ReverseALinkedList().new SinglyLinkedList();

        // Directly generate the list: 1 -> 2 -> 3 -> 4 -> 5
        int[] values = {1, 2, 3, 4, 5};
        for (int value : values) {
            llist.insertNode(value);
        }

        // Reverse the list
        llist.reverse();

        // Print the reversed list
        llist.printList();
    }

}




