package com.amsen.par.cewrncyconv.base.event;

import android.util.Log;

import com.amsen.par.cewrncyconv.base.functional.Action1;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple event eventBus, you cant unsub for simplicity.
 *
 * @author Pär Amsen 2016
 */
public class EventBus<E extends Event> {
    private List<Action1<E>> listeners;

    public EventBus() {
        listeners = new ArrayList<>();
    }

    public void post(E event) {
        for(Action1<E> listener : listeners) {
            listener.call(event);
        }

        Log.d(EventBus.class.getName(), "Event: " + event.toString());
    }

    public void subscribe(Action1<E> listener) {
        listeners.add(listener);
    }
}
