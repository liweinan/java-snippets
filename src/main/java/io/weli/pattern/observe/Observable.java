package io.weli.pattern.observe;

public interface Observable<T> {

    void addObserve(Observe<T> observer);

    void removeObserve(Observe<T> observer);

    void notifyObserves(T data);
}
