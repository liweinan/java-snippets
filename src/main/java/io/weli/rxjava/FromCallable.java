package io.weli.rxjava;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.TestSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.NoSuchElementException;

public class FromCallable {
    enum Irrelevant {INSTANCE;}

    public static void main(String[] args) throws Exception {
        Observable<Object> source = Observable.create((ObservableEmitter<Object> emitter) -> {
            System.out.println("Side-effect 1");
            emitter.onNext(Irrelevant.INSTANCE);

            System.out.println("Side-effect 2");
            emitter.onNext(Irrelevant.INSTANCE);

            System.out.println("Side-effect 3");
            emitter.onNext(Irrelevant.INSTANCE);
        });

        source.subscribe(e -> System.out.println(" e -> " + e), Throwable::printStackTrace);

        {
            List<Integer> list = Flowable.range(1, 100).toList().blockingGet(); // toList() returns Single
            for (int i : list) {
                System.out.println(i);
            }
        }

        {
            Subscriber<Integer> subscriber = new Subscriber<Integer>() {
                @Override
                public void onSubscribe(Subscription s) {
                    s.request(Long.MAX_VALUE);
                }

                public void onNext(Integer t) {
                    if (t == 1) {
//                        throw new IllegalArgumentException();
                    }
                }

                public void onError(Throwable e) {
                    if (e instanceof IllegalArgumentException) {
//                        throw new UnsupportedOperationException();
                    }
                }

                public void onComplete() {
//                    throw new NoSuchElementException();
                }
            };

            Flowable.just(2).subscribe(subscriber);

        }

        {
            Flowable.range(1, 5).test().assertResult(1, 2, 3, 4, 5);
        }

        {
            Flowable.range(1, 5)
                    .test(0)
                    .assertValues()
                    .requestMore(1)
                    .assertValues(1)
                    .requestMore(2)
                    .assertValues(1, 2, 3)
                    .requestMore(2)
                    .assertResult(1, 2, 3, 4, 5);
        }

        {
            PublishProcessor<Integer> pp = PublishProcessor.create();

            TestSubscriber<Integer> ts = pp.test(0L);

            ts.request(1);

            pp.onNext(1);
            pp.onNext(2);

        }

    }
}
