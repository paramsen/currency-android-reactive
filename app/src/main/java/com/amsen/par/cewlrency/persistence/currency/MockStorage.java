package com.amsen.par.cewlrency.persistence.currency;

import com.amsen.par.cewlrency.model.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Mock implementation of storage. Switch impls for the real deal with persistence & stuff.
 *
 * @author Pär Amsen 2016
 */
public class MockStorage implements CurrencyStorage {
    private HashMap<String, Currency> storage;

    public MockStorage() {
        storage = new HashMap<>();
    }

    @Override
    public void put(Currency currency) {
        storage.put(currency.getId(), currency);
    }

    @Override
    public void put(List<Currency> currencies) {
        for (Currency currency : currencies) {
            storage.put(currency.getId(), currency);
        }
    }

    @Override
    public Observable<Currency> get(String id) {
        return Observable.create(subscriber -> {
            Currency currency = storage.get(id);

            if (currency != null) {
                subscriber.onNext(currency);
            }

            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<List<Currency>> getAll() {
        return Observable.create(subscriber -> {
            ArrayList<Currency> list = new ArrayList<>(storage.values());

            if (!list.isEmpty()) {
                subscriber.onNext(list);
            }

            subscriber.onCompleted();
        });
    }

    @Override
    public void invalidate() {
        storage.clear();
    }
}
