package com.amsen.par.cewlrency.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.util.ViewUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.view.CurrencyEvent;
import com.amsen.par.cewlrency.view.view.currencypicker.CurrencyPickerRecycler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Wrapper to add the arrow stuff ontop of the pager
 * (simpler to use the existing gfx framework than to use onDraw)
 *
 * @author Pär Amsen 2016
 */
public class CurrencyPicker extends FrameLayout {
    @BindView(R.id.currencyRecycler)
    CurrencyPickerRecycler currencyRecycler;

    public CurrencyPicker(Context context) {
        super(context);
        init(context);
    }

    public CurrencyPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurrencyPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CurrencyPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        ViewUtils.inflate(context, R.layout.view_currency_picker, this, true, (activity, view) -> {
            ButterKnife.bind(this, view);
        });
    }

    public void applyItems(List<Currency> items, String initialCurrency) {
        currencyRecycler.applyItems(items, initialCurrency);
    }

    public void setEventType(CurrencyEvent.Type eventType) {
        currencyRecycler.setEventType(eventType);
    }
}