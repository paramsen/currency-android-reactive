package com.amsen.par.cewlrency.api.fixerio.model;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyRateRespModel {
    public final String id;
    public final double value;

    public CurrencyRateRespModel(String id, double value) {
        this.id = id;
        this.value = value;
    }
}
