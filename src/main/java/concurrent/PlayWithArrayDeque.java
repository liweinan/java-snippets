package concurrent;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.Assert.assertEquals;

public class PlayWithArrayDeque {

    // use it as stack

    public void whenPush_addsAtFirst() {
        Deque<String> stack = new ArrayDeque<>();
        stack.push("first");
        stack.push("second");
        assertEquals("second", stack.getFirst());
    }

    public void whenPop_removesLast() {
        Deque<String> stack = new ArrayDeque<>();
        stack.push("first");
        stack.push("second");
        assertEquals("second", stack.pop());
    }

    // use it as queue

    public void whenOffer_addsAtLast() {
        Deque<String> queue = new ArrayDeque<>();
        queue.offer("first");
        queue.offer("second");

        assertEquals("second", queue.getLast());
    }

    public void whenPoll_removesFirst() {
        Deque<String> queue = new ArrayDeque<>();
        queue.offer("first");
        queue.offer("second");
        assertEquals("first", queue.poll());
    }

    public static void main(String[] args) throws Exception {
        PlayWithArrayDeque play = new PlayWithArrayDeque();
        play.whenPush_addsAtFirst();
        play.whenPop_removesLast();
    }
}
