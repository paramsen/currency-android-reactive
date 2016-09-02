package com.amsen.par.cewlrency.base.subscriber;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observers.Subscribers;

/**
 * @author Pär Amsen 2016
 */
public class SubscriberUtils {
    public static <T> Subscriber<T> onNext(Action1<T> onNext) {
        return Subscribers.create(onNext, e -> {});
    }

    public static <T> Subscriber<T> onError(Action1<Throwable> onError) {
        return Subscribers.create(e -> {}, onError);
    }

    public static <T> Subscriber<T> onComplete(Action0 onComplete) {
        return Subscribers.create(e -> {}, e -> {}, onComplete);
    }

    public static <T> Subscriber<T> onNextOnComplete(Action1<T> onNext, Action0 onComplete) {
        return Subscribers.create(onNext, e -> {}, onComplete);
    }
}
