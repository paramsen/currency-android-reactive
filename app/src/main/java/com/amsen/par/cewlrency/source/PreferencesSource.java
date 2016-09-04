package com.amsen.par.cewlrency.source;

import com.amsen.par.cewlrency.persistence.preferences.PersistentStorage;

/**
 * @author Pär Amsen 2016
 */
public class PreferencesSource {
    private PersistentStorage storage;

    public PreferencesSource(PersistentStorage storage) {
        this.storage = storage;
    }

    public   <T> void put(String key, T value) {
        storage.put(key, value);
    }

    public <T> T get(String key) {
        return storage.get(key);
    }
}
