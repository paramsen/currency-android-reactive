package com.amsen.par.cewrncyconv.base;

import android.app.Application;

import com.amsen.par.cewrncyconv.api.CurrencyApi;
import com.amsen.par.cewrncyconv.base.dependencies.AppGraph;
import com.amsen.par.cewrncyconv.persistence.MockStorage;
import com.amsen.par.cewrncyconv.source.CurrencySource;

import java.util.Locale;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyApplication extends Application {
    private AppGraph appGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        appGraph = new AppGraph(new CurrencySource(new CurrencyApi(), new MockStorage()));
    }

    public AppGraph getAppGraph() {
        return appGraph;
    }
}
