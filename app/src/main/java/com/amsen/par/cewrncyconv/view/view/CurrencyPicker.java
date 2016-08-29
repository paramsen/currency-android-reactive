package com.amsen.par.cewrncyconv.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.amsen.par.cewrncyconv.R;
import com.amsen.par.cewrncyconv.base.event.EventBus;
import com.amsen.par.cewrncyconv.model.Currency;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyPicker extends FrameLayout {
    @BindView(R.id.viewPager)
    CurrencyPickerViewPager viewPager;

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
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_currency_picker, this, true);
        ButterKnife.bind(this, view);
    }

    public void applyItems(List<Currency> items) {
        viewPager.applyItems(items);
    }

    public void setEventBus(EventBus eventBus) {
        viewPager.setEventBus(eventBus);
    }
}