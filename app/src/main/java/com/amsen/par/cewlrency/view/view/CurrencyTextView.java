package com.amsen.par.cewlrency.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.base.util.ViewUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.view.CurrencyEvent;
import com.amsen.par.cewlrency.view.activity.BaseActivity;

import java.text.NumberFormat;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyTextView extends TextView {
    @Inject
    EventStream eventStream;

    private Currency currency;
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
        ViewUtils.getBaseActivity(context).getComponent().inject(this);
        initBehavior();
    }

    private void initBehavior() {
        eventStream.stream()
                .filter(e -> e instanceof CurrencyEvent)
                .cast(CurrencyEvent.class)
                .subscribe(SubscriberUtils.onNext(this::onAmountChanged));
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
