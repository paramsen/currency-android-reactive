package com.amsen.par.cewrncyconv.persistence;

import com.amsen.par.cewrncyconv.model.Currency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Mock implementation of storage. Switch impls for the real deal with persistence & stuff.
 *
 * @author Pär Amsen 2016
 */
public class MockStorage implements Storage<Currency> {
    private HashMap<String, Currency> storage;

    public MockStorage() {
        storage = new HashMap<>();
    }

    @Override
    public void put(Currency videoStatus) {
        storage.put(videoStatus.getId(), videoStatus);
    }

    @Override
    public Currency get(int id) {
        return storage.get(id);
    }

    @Override
    public List<Currency> getAll() {
        ArrayList<Currency> list = new ArrayList<>(storage.values());
        Collections.sort(list, (a, b) -> a.getId().compareTo(b.getId()));

        return list;
    }

    @Override
    public void invalidate() {
        storage.clear();
    }
}
