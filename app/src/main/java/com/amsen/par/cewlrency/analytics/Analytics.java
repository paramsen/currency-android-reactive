package com.amsen.par.cewlrency.analytics;

import android.os.Bundle;

import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.view.CurrencyEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

/**
 * @author Pär Amsen 2016
 */
public class Analytics {
    private FirebaseAnalytics analytics;

    @Inject
    public Analytics(FirebaseAnalytics analytics) {
        this.analytics = analytics;
    }

    public void newFavourite(String from, String to) {
        Bundle bundle = new Bundle();
        bundle.putString("from_currency", from);
        bundle.putString("to_currency", to);

        analytics.logEvent("new_favourite", bundle);
    }

    public void alreadyChosenFavourite(String from, String to) {
        Bundle bundle = new Bundle();
        bundle.putString("from_currency", from);
        bundle.putString("to_currency", to);

        analytics.logEvent("already_chosen_favourite", bundle);
    }

    public void changeAmount(double value) {
        Bundle bundle = new Bundle();
        bundle.putDouble("amount", value);

        analytics.logEvent("change_amount", bundle);
    }

    public void changeCurrency(CurrencyEvent.Type type, Currency currency) {
        Bundle bundle = new Bundle();

        if (type == CurrencyEvent.Type.CHANGE_CURRENCY_FROM) {
            bundle.putString("type", "from");
        } else {
            bundle.putString("type", "to");
        }

        bundle.putString("currency_id", currency.getId());

        analytics.logEvent("change_currency", bundle);
    }
}
