package com.amsen.par.cewlrency.model;

import android.support.annotation.NonNull;

/**
 * @author Pär Amsen 2016
 */
public class Currency implements Comparable<Currency> {
    public static final String BASE_ID = "SEK";
    private String id;
    private double rate;
    private java.util.Currency currency;

    public Currency(String id, double rate, java.util.Currency currency) {
        this.id = id;
        this.rate = rate;
        this.currency = currency;
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

    public java.util.Currency getCurrency() {
        return currency;
    }

    public void setCurrency(java.util.Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        return id.equals(currency.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(@NonNull  Currency o) {
        return id.compareTo(o.getId());
    }
}
