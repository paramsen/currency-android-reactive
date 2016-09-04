package com.amsen.par.cewlrency.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.base.util.CurrencyUtil;
import com.amsen.par.cewlrency.base.util.ViewUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.source.CurrencySource;
import com.amsen.par.cewlrency.view.CurrencyEvent;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyTextView extends TextView {
    @Inject
    EventStream eventStream;
    @Inject
    CurrencySource source;

    private Currency currencyFrom;
    private Currency currencyTo;
    private Currency baseCurrency;
    private double amount;

    public CurrencyTextView(Context context) {
        super(context);
        init(context);
    }

    public CurrencyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurrencyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            ViewUtils.getBaseActivity(context).getComponent().inject(this);
            initialState();
        }
    }

    private void initialState() {
        source.getCurrency(Currency.BASE_ID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SubscriberUtils.onNext(this::onBaseCurrency));
    }

    private void onBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
        initBehavior();
    }

    private void initBehavior() {
        eventStream.stream()
                .filter(e -> e instanceof CurrencyEvent)
                .cast(CurrencyEvent.class)
                .subscribe(SubscriberUtils.onNext(this::onAmountChanged));
    }

    private void onAmountChanged(CurrencyEvent currencyEvent) {
        if (currencyEvent.type == CurrencyEvent.Type.CHANGE_CURRENCY_FROM) {
            currencyFrom = (Currency) currencyEvent.value;
        } else if (currencyEvent.type == CurrencyEvent.Type.CHANGE_CURRENCY_TO) {
            currencyTo = (Currency) currencyEvent.value;
        } else {
            amount = (double) currencyEvent.value;
        }

        if (currencyFrom != null && currencyTo != null) {
            double multiplier = baseCurrency.getRate() / currencyFrom.getRate();
            double conversionRate = currencyTo.getRate() * multiplier;

            setText(CurrencyUtil.format(currencyTo.getCurrency(), amount * conversionRate));
        }
    }
}
