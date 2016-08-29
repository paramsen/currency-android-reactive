package com.amsen.par.cewrncyconv.base.dependencies;

import com.amsen.par.cewrncyconv.base.event.EventBus;
import com.amsen.par.cewrncyconv.source.CurrencySource;
import com.amsen.par.cewrncyconv.view.CurrencyEvent;

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
    public final EventBus<CurrencyEvent> eventBus;

    public ViewGraph(CurrencySource currencySource) {
        this.currencySource = currencySource;
        this.eventBus = new EventBus<>();
    }
}
