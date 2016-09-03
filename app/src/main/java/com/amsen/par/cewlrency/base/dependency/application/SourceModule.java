package com.amsen.par.cewlrency.base.dependency.application;

import com.amsen.par.cewlrency.api.fixerio.FixerIoResource;
import com.amsen.par.cewlrency.persistence.currency.MockStorage;
import com.amsen.par.cewlrency.source.CurrencySource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author Pär Amsen 2016
 */
@Module
public class SourceModule {
    @Provides
    @Singleton
    public CurrencySource provideWeatherSource(FixerIoResource.ApiAccess apiAccess, MockStorage currencyStorage) {
        return new CurrencySource(apiAccess, currencyStorage);
    }
}
