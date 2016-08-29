package com.amsen.par.cewrncyconv.source;

import com.amsen.par.cewrncyconv.api.CurrencyApi;
import com.amsen.par.cewrncyconv.base.functional.Action1;
import com.amsen.par.cewrncyconv.model.Currency;
import com.amsen.par.cewrncyconv.persistence.Storage;

import java.util.List;

import static com.amsen.par.cewrncyconv.base.functional.Async.go;

/**
 * A Source is a concept;
 * Seen as a Single Source of Truth the Source is the
 * only access point to all data in the app. Similar to
 * data layer etc. You get the data through a Source
 * and you save it through one (or a Sink for that
 * matter).
 * <p>
 * Note separation of protected sync getCurrencies()
 * and public async version.
 *
 * @author Pär Amsen 2016
 */
public class CurrencySource {
    private CurrencyApi api;
    private Storage<Currency> cache;

    protected List<Currency> getCurrencies() {
        List<Currency> currencies = cache.getAll();

        if (currencies.size() == 0) {
            currencies = api.getCurrencies();
        }

        return currencies;
    }

    /**
     * Async version. Looks proper in Java8. Better in
     * RxJava. In "vanilla" Android I keep async details
     * in the Source layer [when possible], it's quite simple
     * this way.
     */
    public void getCurrencies(Action1<List<Currency>> callback) {
        go(this::getCurrencies, callback);
    }
}
