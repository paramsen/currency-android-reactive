package com.amsen.par.cewlrency.analytics;

import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.view.CurrencyEvent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

import static com.amsen.par.cewlrency.view.CurrencyEvent.Type.CHANGE_AMOUNT;
import static com.amsen.par.cewlrency.view.CurrencyEvent.Type.CHANGE_CURRENCY_FROM;
import static com.amsen.par.cewlrency.view.CurrencyEvent.Type.CHANGE_CURRENCY_TO;

/**
 * @author Pär Amsen 2016
 */
public class BehaviorTracker {
    private Analytics analytics;
    private EventStream eventStream;

    @Inject
    public BehaviorTracker(Analytics analytics, EventStream eventStream) {
        this.analytics = analytics;
        this.eventStream = eventStream;
    }

    public void start() {
        Observable<CurrencyEvent> events = eventStream.stream()
                .filter(e -> e instanceof CurrencyEvent)
                .cast(CurrencyEvent.class)
                .share();

        events.filter(e -> e.type == CHANGE_CURRENCY_FROM)
                .throttleWithTimeout(5000, TimeUnit.MILLISECONDS, Schedulers.computation())
                .skip(1)
                .subscribe(SubscriberUtils.onNext(this::changeCurrencyEvent));

        events.filter(e -> e.type == CHANGE_CURRENCY_TO)
                .throttleWithTimeout(5000, TimeUnit.MILLISECONDS, Schedulers.computation())
                .skip(1)
                .subscribe(SubscriberUtils.onNext(this::changeCurrencyEvent));

        events.filter(e -> e.type == CHANGE_AMOUNT)
                .throttleWithTimeout(5000, TimeUnit.MILLISECONDS, Schedulers.computation())
                .subscribe(SubscriberUtils.onNext(this::changeAmount));
    }

    private void changeAmount(CurrencyEvent event) {
        analytics.changeAmount((double) event.value);
    }

    private void changeCurrencyEvent(CurrencyEvent event) {
        analytics.changeCurrency(event.type, (Currency) event.value);
    }
}
