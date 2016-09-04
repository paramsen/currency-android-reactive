package com.amsen.par.cewlrency.view.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.analytics.Analytics;
import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.base.util.ViewUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.persistence.preferences.PreferencesHelper;
import com.amsen.par.cewlrency.source.PreferencesSource;
import com.amsen.par.cewlrency.view.CurrencyEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Pär Amsen 2016
 */
public class FavoriteFAB extends FloatingActionButton {
    @Inject
    EventStream eventStream;
    @Inject
    PreferencesSource preferencesSource;
    @Inject
    Analytics analytics;

    private Currency from;
    private Currency to;

    public FavoriteFAB(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FavoriteFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            ViewUtils.getBaseActivity(context).getComponent().inject(this);
            ButterKnife.bind(this);

            setupBehavior();
        }
    }

    @OnClick
    public void onClick() {
        String fromId = preferencesSource.<String>get(PreferencesHelper.CURRENCY_FROM);
        String toId = preferencesSource.<String>get(PreferencesHelper.CURRENCY_TO);

        if (fromId == null || toId == null || (!fromId.equals(from.getId()) || !toId.equals(to.getId()))) {
            preferencesSource.put(PreferencesHelper.CURRENCY_FROM, from.getId());
            preferencesSource.put(PreferencesHelper.CURRENCY_TO, to.getId());

            onFavoriteConversion();

            Snackbar.make(this, getResources().getString(R.string.ARG1_to_ARG2_set_as_your_standard_conversion, from.getId(), to.getId()), Snackbar.LENGTH_LONG).show();

            analytics.newFavourite(from.getId(), to.getId());
        } else {
            Snackbar.make(this, R.string.This_is_already_your_standard_conversion, Snackbar.LENGTH_LONG).show();
            analytics.alreadyChosenFavourite(fromId, toId);
        }
    }

    private void setupBehavior() {
        eventStream.stream()
                .filter(e -> e instanceof CurrencyEvent)
                .cast(CurrencyEvent.class)
                .subscribe(SubscriberUtils.onNext(this::onCurrencyEvent));
    }

    private void onCurrencyEvent(CurrencyEvent event) {
        if (event.type == CurrencyEvent.Type.CHANGE_CURRENCY_FROM) {
            from = (Currency) event.value;
        } else if (event.type == CurrencyEvent.Type.CHANGE_CURRENCY_TO) {
            to = (Currency) event.value;
        }

        if (from != null && to != null) {
            String fromId = preferencesSource.<String>get(PreferencesHelper.CURRENCY_FROM);
            String toId = preferencesSource.<String>get(PreferencesHelper.CURRENCY_TO);

            if (from.getId().equals(fromId) && to.getId().equals(toId)) {
                onFavoriteConversion();
            } else {
                onNotFavoriteConversion();
            }
        }
    }

    private void onFavoriteConversion() {
        setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.colorAccent));
    }

    private void onNotFavoriteConversion() {
        setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.fabIconNormal));
    }
}
