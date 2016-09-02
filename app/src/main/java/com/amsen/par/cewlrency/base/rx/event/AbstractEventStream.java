package com.amsen.par.cewlrency.base.rx.event;

import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * @author Pär Amsen @ Nixon Nixon / 2016
 */
public class AbstractEventStream<T> {
    private PublishSubject<T> stream = PublishSubject.create();
    private Observable<T> historyStream;
    private BehaviorSubject<T> mostRecentStream;

    @Inject
    public AbstractEventStream() {
        stream.subscribe(SubscriberUtils.onNextOnComplete(next -> mostRecentStream.onNext(next), () -> mostRecentStream.onCompleted()));
        historyStream = stream().replay().autoConnect();
        mostRecentStream = BehaviorSubject.create();

        historyStream().subscribe(SubscriberUtils.onComplete(() -> {
            //just subscribe to init stream
        }));
    }

    public void post(T t) {
        stream.onNext(t);
    }

    public Observable<T> stream() {
        return stream;
    }

    /**
     * @return an Observable that repeats the EventStreams events (look at Observable.repeat)
     */
    public Observable<T> historyStream() {
        return historyStream;
    }

    /**
     * @return an Observable that repeats the last emitted item and following items
     */
    public Observable<T> mostRecentStream() {
        return mostRecentStream;
    }

    public void complete() {
        stream.onCompleted();
    }
}
