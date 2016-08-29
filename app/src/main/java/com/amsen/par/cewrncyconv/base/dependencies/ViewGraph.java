package com.amsen.par.cewrncyconv.base.dependencies;

import com.amsen.par.cewrncyconv.source.CurrencySource;

/**
 * Dependency Graph for the View layer. Dependencies
 * here live alongside the Activity lifecycle.
 *
 * Naturally this would be replaced by
 * Dagger2 or Guice.
 *
 * @author Pär Amsen 2016
 */
public class ViewGraph {
    public final CurrencySource currencySource;

    public ViewGraph(CurrencySource currencySource) {
        this.currencySource = currencySource;
    }
}
