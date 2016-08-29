package com.amsen.par.cewrncyconv.base.dependencies;

import com.amsen.par.cewrncyconv.source.CurrencySource;

/**
 * Dependency Graph on Application level, all deps
 * declared here are Singletons per se as they live
 * alongside the Application lifecycle.
 *
 * Naturally this would be replaced by
 * Dagger2 or Guice.
 *
 * @author Pär Amsen 2016
 */
public class AppGraph {
    public final CurrencySource currencySource;

    public AppGraph(CurrencySource currencySource) {
        this.currencySource = currencySource;
    }
}
