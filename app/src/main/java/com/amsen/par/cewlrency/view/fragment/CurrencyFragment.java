package com.amsen.par.cewlrency.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.source.CurrencySource;
import com.amsen.par.cewlrency.view.view.CurrencyEditText;
import com.amsen.par.cewlrency.view.view.CurrencyPicker;
import com.amsen.par.cewlrency.view.view.CurrencyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyFragment extends Fragment {
    @BindView(R.id.currencyPicker)
    CurrencyPicker currencyPicker;
    @BindView(R.id.currencyEditText)
    CurrencyEditText currencyEditText;
    @BindView(R.id.currencyTextView)
    CurrencyTextView currencyTextView;

    private CurrencySource source;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        currencyEditText.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //Android does not allow dashed strokes on accelerated devices..

        initialState();
    }

    private void initialState() {
        source.getCurrencies().subscribe(SubscriberUtils.onNext(this::onCurrencies));
    }

    private void onCurrencies(List<Currency> currencies) {
        currencyPicker.applyItems(currencies);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currency, container, false);
    }
}
