package com.amsen.par.cewrncyconv.view;

import com.amsen.par.cewrncyconv.base.event.Event;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyEvent<E> implements Event {
    public enum Type {
        CHANGE_CURRENCY, CHANGE_AMOUNT
    }

    public CurrencyEvent(E value) {
        this.value = value;
    }

    public final E value;
}
