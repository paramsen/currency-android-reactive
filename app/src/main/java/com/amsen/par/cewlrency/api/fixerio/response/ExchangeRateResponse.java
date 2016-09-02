package com.amsen.par.cewlrency.api.fixerio.response;

import com.amsen.par.cewlrency.api.fixerio.model.RateRespModel;

import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public class ExchangeRateResponse {
    public final String base;
    public final String date;
    public final List<RateRespModel> rates;

    public ExchangeRateResponse(String base, String date, List<RateRespModel> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }
}
