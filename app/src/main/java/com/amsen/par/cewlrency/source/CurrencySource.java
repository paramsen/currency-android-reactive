package com.amsen.par.cewlrency.source;

import com.amsen.par.cewlrency.api.fixerio.FixerIoResource.ApiAccess;
import com.amsen.par.cewlrency.api.fixerio.model.CurrencyRateRespModel;
import com.amsen.par.cewlrency.api.fixerio.response.CurrencyExchangeRatesResponse;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.persistence.currency.CurrencyStorage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * A Source is a concept;
 * Seen as a Single Source of Truth the Source is the
 * only access point to all data in the app. Similar to
 * data layer etc. You get the data through a Source
 * and you save it through one (or a Sink for that
 * matter).
 *
 * @author Pär Amsen 2016
 */
public class CurrencySource {
    private ApiAccess api;
    private CurrencyStorage cache;

    public CurrencySource(ApiAccess api, CurrencyStorage cache) {
        this.api = api;
        this.cache = cache;
    }

    public Observable<List<Currency>> getCurrencies() {
        return cache.getAll()
                .switchIfEmpty(fromApi());
    }

    public Observable<Currency> getCurrency(String id) {
        return cache.get(id)
                .switchIfEmpty(fromApi().flatMap(Observable::from).filter(e -> e.getId().equals(id)).first());
    }

    protected Observable<List<Currency>> fromApi() {
        return api.getCurrencyExchangeRates()
                .map(Response::body)
                .map(this::transform)
                .map(this::addBase)
                .doOnNext(cache::put);
    }

    private List<Currency> addBase(List<Currency> currencies) {
        currencies.add(new Currency(Currency.BASE_ID, 1.0, java.util.Currency.getInstance(Currency.BASE_ID)));

        return currencies;
    }

    private List<Currency> transform(CurrencyExchangeRatesResponse resp) {
        List<Currency> currencies = new ArrayList<>();

        for (CurrencyRateRespModel model : resp.getRates()) {
            currencies.add(new Currency(model.id, model.value, java.util.Currency.getInstance(model.id)));
        }

        return currencies;
    }
}
