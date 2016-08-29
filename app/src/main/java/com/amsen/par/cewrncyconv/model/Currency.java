package com.amsen.par.cewrncyconv.model;

/**
 * @author Pär Amsen 2016
 */
public class Currency {
    private String id;
    private double rate;

    public Currency(String id, double rate) {
        this.id = id;
        this.rate = rate;
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
}
