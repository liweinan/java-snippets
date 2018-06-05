package io.weli.rxjava;

import io.reactivex.Observable;

public class DemoThrowing {
    public static void main(String[] args) {
        Observable<String> source = Observable.create(task -> {
            try {
                task.onNext("A");
                throw new Exception("Hello, Martian!");
            } catch (Throwable e) {
                task.onError(e);
            }
        });

        source.subscribe(result -> System.out.println("RECEIVED: " + result),
                e -> {
                    System.out.println("ERROR: " + e.getMessage());
                });
    }
}
