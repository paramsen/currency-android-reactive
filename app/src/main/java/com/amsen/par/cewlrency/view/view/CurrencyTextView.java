package com.amsen.par.cewlrency.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.amsen.par.cewlrency.base.event.EventBus;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.view.CurrencyEvent;

import java.text.NumberFormat;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyTextView extends TextView {
    private Currency currency;
    private double amount;

    public CurrencyTextView(Context context) {
        super(context);
    }

    public CurrencyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrencyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CurrencyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void applyEventBus(EventBus<CurrencyEvent> eventBus) {
        eventBus.subscribe(this::onAmountChanged);
    }

    private void onAmountChanged(CurrencyEvent currencyEvent) {
        if(currencyEvent.type == CurrencyEvent.Type.CHANGE_CURRENCY) {
            currency = (Currency) currencyEvent.value;
        } else {
            amount = (double) currencyEvent.value;
        }

        if(currency != null) {
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currency.getLocale());
            setText(currencyFormatter.format(amount * currency.getRate()));
        }
    }
}
