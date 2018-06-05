package io.weli.rxjava;

import io.reactivex.Observable;

public class ThreeEvents {
    public static void main(String[] args) {

        System.out.println(Math.tan(Math.toRadians(90)));

        System.out.println("90 to Radians: " + Math.toRadians(90));
        System.out.println("PI / 2: " + Math.PI / 2);
        System.out.println(Math.log(0));
        System.out.println(Math.log(1));
        System.out.println(Math.log(-1));

        Observable<String> source =
                Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon", String.valueOf(Math.tan(Math.PI / 2)));

        source.map(String::length).filter(i -> i >= 5)
                .subscribe(i -> System.out.println("RECEIVED: " + i),
                        e -> System.out.println(e),
                        () -> System.out.println("Done!"));
    }
}
