package io.weli.pattern.observe;

import java.util.ArrayList;
import java.util.List;

public class ObserveDemo {

    static class NewsPublisher implements Observable<String> {

        private final List<Observe<String>> observers = new ArrayList<>();

        @Override
        public void addObserve(Observe<String> observer) {
            observers.add(observer);
        }

        @Override
        public void removeObserve(Observe<String> observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObserves(String data) {
            for (Observe<String> observer : observers) {
                observer.update(data);
            }
        }

        public void publish(String headline) {
            notifyObserves(headline);
        }
    }

    static class NewsSubscriber implements Observe<String> {

        private final String name;

        NewsSubscriber(String name) {
            this.name = name;
        }

        @Override
        public void update(String data) {
            System.out.println(name + " received: " + data);
        }
    }

    public static void main(String[] args) {
        NewsPublisher publisher = new NewsPublisher();
        Observe<String> channelA = new NewsSubscriber("Channel-A");
        Observe<String> channelB = new NewsSubscriber("Channel-B");

        publisher.addObserve(channelA);
        publisher.addObserve(channelB);
        publisher.publish("Observer pattern demo");

        publisher.removeObserve(channelA);
        publisher.publish("Only Channel-B gets this");
    }
}
