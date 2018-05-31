package io.weli.rxjava;

import io.reactivex.Maybe;

public class PlayWithRxJava {
    public static void main(String[] args) {
        Maybe.just(1)
                .map(v -> v + 1)
                .filter(v -> v == 1)
                .defaultIfEmpty(2)
                .test()
                .assertResult(2);

    }
}
