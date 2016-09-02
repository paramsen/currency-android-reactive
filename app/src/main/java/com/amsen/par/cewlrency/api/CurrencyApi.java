package com.amsen.par.cewlrency.api;

import com.amsen.par.cewlrency.model.Currency;

import java.util.List;

import rx.Observable;

/**
 * API mapping for Fixer.io service
 *
 * @author Pär Amsen 2016
 */
public class CurrencyApi {
    public Observable<List<Currency>> getCurrencies() {
        return Observable.empty();
    }
}
