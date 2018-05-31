package io.weli.rxjava;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class Launcher {
    public static void main(String[] args) {
        Observable<String> myStrings =
                Observable.just("Alpha", "Beta", "Gamma", "Delta",
                        "Epsilon");

        myStrings.subscribe(s -> {
            System.out.print("Receiving: ");
            System.out.println(s);
        });

        myStrings.map(s -> s.length()).subscribe(s -> System.out.println(s));
    }
}
