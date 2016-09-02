package com.amsen.par.cewlrency.base.event;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import static com.amsen.par.cewlrency.base.subscriber.SubscriberUtils.onComplete;
import static com.amsen.par.cewlrency.base.subscriber.SubscriberUtils.onNextOnComplete;

/**
 * Rx version of otto.EventBus [the largest EventBus lib for Android]. Streamable instead and expose a better api.
 *
 * @author Pär Amsen 2016
 */
public class EventStream {
    private PublishSubject<Event> stream = PublishSubject.create();
    private Observable<Event> historyStream;
    private BehaviorSubject<Event> mostRecentStream;

    public EventStream() {
        stream.subscribe(onNextOnComplete(next -> mostRecentStream.onNext(next), () -> mostRecentStream.onCompleted()));
        historyStream = stream().replay().autoConnect();
        mostRecentStream = BehaviorSubject.create();

        historyStream().subscribe(onComplete(() -> {
            //just subscribe to init stream
        }));
    }

    public void post(Event t) {
        stream.onNext(t);
    }

    public Observable<Event> stream() {
        return stream;
    }

    /**
     * @return an Observable that repeats the EventStreams events (look at Observable.repeat)
     */
    public Observable<Event> historyStream() {
        return historyStream;
    }

    /**
     * @return an Observable that repeats the last emitted item and following items
     */
    public Observable<Event> mostRecentStream() {
        return mostRecentStream;
    }

    public void complete() {
        stream.onCompleted();
    }
}
