package com.amsen.par.cewlrency.source;

import com.amsen.par.cewlrency.api.fixerio.FixerIoResource.ApiAccess;
import com.amsen.par.cewlrency.api.fixerio.response.CurrencyExchangeRatesResponse;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.persistence.currency.CurrencyStorage;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

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

    protected Observable<List<Currency>> fromApi() {
        return api.getCurrencyExchangeRates()
                .map(this::transform)
                .doOnNext(cache::put);
    }

    private List<Currency> transform(Response<CurrencyExchangeRatesResponse> resp) {
        return Collections.emptyList();
    }
}
