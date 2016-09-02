package com.amsen.par.cewlrency.base.rx.event;

import com.amsen.par.cewlrency.BuildConfig;

/**
 * @author Pär Amsen @ Nixon Nixon / 2016
 */
public class EventStream extends AbstractEventStream<Event> {
    public EventStream() {
        super();

        stream().doOnCompleted(() -> {
            if (BuildConfig.DEBUG) {
                throw new RuntimeException("AbstractEventStream should not be completed");
            }
        });
    }
}
