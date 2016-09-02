package com.amsen.par.cewlrency.base.dependency.application;

import com.amsen.par.cewlrency.base.CurrencyApplication;
import com.amsen.par.cewlrency.base.rx.event.EventStream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Pär Amsen 2016
 */
@Module
public class ApplicationModule {
    private CurrencyApplication app;

    public ApplicationModule(CurrencyApplication app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public CurrencyApplication provideApplication() {
        return app;
    }

    @Singleton
    @Provides
    public EventStream provideEventStream() {
        return new EventStream();
    }
}
