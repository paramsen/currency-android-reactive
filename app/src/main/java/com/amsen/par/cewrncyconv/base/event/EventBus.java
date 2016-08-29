package com.amsen.par.cewrncyconv.base.event;

import com.amsen.par.cewrncyconv.base.functional.Action1;

import java.util.List;

/**
 * Simple event eventBus, you cant unsub for simplicity.
 *
 * @author Pär Amsen 2016
 */
public class EventBus<E extends Event> {
    private List<Action1<E>> listeners;

    public void post(E event) {
        for(Action1<E> listener : listeners) {
            listener.call(event);
        }
    }

    public void subscribe(Action1<E> listener) {
        listeners.add(listener);
    }
}
