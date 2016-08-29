package com.amsen.par.cewrncyconv.model;

import java.util.Locale;

/**
 * @author Pär Amsen 2016
 */
public class Currency {
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
}
