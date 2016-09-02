package com.amsen.par.cewlrency.model;

import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * @author Pär Amsen 2016
 */
public class Currency implements Comparable<Currency> {
    private String id;
    private double rate;
    private Locale locale;

    public Currency(String id, double rate, Locale locale) {
        this.id = id;
        this.rate = rate;
        this.locale = locale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public int compareTo(@NonNull  Currency o) {
        return id.compareTo(o.getId());
    }
}
