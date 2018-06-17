package io.weli.rxjava;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class JustFilter {
    public static void main(String[] args) {
        List<String> items =
                Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon");

        Observable<String> source = Observable.fromIterable(items);
//        source.map(String::length).filter(i -> i >= 5);
        source.map(String::length).filter(i -> i >= 5)
                .subscribe(s -> System.out.println("RECEIVED: " + s));
    }
}
