package io.weli.rxjava;

import io.reactivex.Observable;

public class ColdStream {
    public static void main(String[] args) {
        Observable<String> source =
                Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        //first observer
        source.subscribe(s -> System.out.println("Observer 1 Received: " + s));

        //second observer
        source.map(String::length).filter(i -> i >= 5)
                .subscribe(s -> System.out.println("Observer 2 Received: " + s));
    }
}
