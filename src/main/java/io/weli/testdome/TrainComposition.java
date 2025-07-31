package io.weli.testdome;

import java.util.LinkedList;
import java.util.Deque;

// - https://www.testdome.com/library?page=1&skillArea=30&questionId=77795
public class TrainComposition {
    private Deque<Integer> train;

    public TrainComposition() {
        train = new LinkedList<>();
    }

    public void attachWagonFromLeft(int wagonId) {
        train.addFirst(wagonId);
    }

    public void attachWagonFromRight(int wagonId) {
        train.addLast(wagonId);
    }

    public int detachWagonFromLeft() {
        if (train.isEmpty()) {
            throw new IllegalStateException("Train is empty");
        }
        return train.removeFirst();
    }

    public int detachWagonFromRight() {
        if (train.isEmpty()) {
            throw new IllegalStateException("Train is empty");
        }
        return train.removeLast();
    }

    public static void main(String[] args) {
        TrainComposition train = new TrainComposition();
        train.attachWagonFromLeft(7);
        train.attachWagonFromLeft(13);
        System.out.println(train.detachWagonFromRight()); // 7
        System.out.println(train.detachWagonFromLeft()); // 13
    }
}
