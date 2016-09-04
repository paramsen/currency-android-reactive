package com.amsen.par.cewlrency.base.dependency.application;

import com.amsen.par.cewlrency.api.fixerio.FixerIoResource;
import com.amsen.par.cewlrency.persistence.preferences.PersistentStorage;
import com.amsen.par.cewlrency.persistence.currency.MockStorage;
import com.amsen.par.cewlrency.source.CurrencySource;
import com.amsen.par.cewlrency.source.PreferencesSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Pär Amsen 2016
 */
@Module
public class SourceModule {
    @Provides
    @Singleton
    public CurrencySource provideCurrencySource(FixerIoResource.ApiAccess apiAccess, MockStorage currencyStorage) {
        return new CurrencySource(apiAccess, currencyStorage);
    }

    @Provides
    @Singleton
    public PreferencesSource providePreferencesSource(PersistentStorage storage) {
        return new PreferencesSource(storage);
    }
}
