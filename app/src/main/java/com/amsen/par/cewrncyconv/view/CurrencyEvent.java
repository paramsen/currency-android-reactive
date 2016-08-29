package com.amsen.par.cewrncyconv.view;

import com.amsen.par.cewrncyconv.base.event.Event;

import org.json.JSONStringer;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyEvent<E> implements Event {
    public enum Type {
        CHANGE_CURRENCY, CHANGE_AMOUNT
    }

    public CurrencyEvent(E value, Type type) {
        this.value = value;
        this.type = type;
    }

    public final E value;
    public final Type type;

    @Override
    public String toString() {
        return String.format("{type: %s, value: %s}", type.name(), value.toString());
    }
}
