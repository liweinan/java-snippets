package io.weli.netty;


import io.reactivex.Observable;

import static org.junit.Assert.assertTrue;

public class RxJavaEvents {
    public static void main(String[] args) {
        String result = null;

        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};

        Observable<String> observable = Observable.fromArray(letters);
//        observable.subscribe((s -> result = s);

        assertTrue(result.equals("abcdefg_Completed"));
    }
}