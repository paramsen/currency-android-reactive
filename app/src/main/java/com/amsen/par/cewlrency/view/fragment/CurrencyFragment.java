package com.amsen.par.cewlrency.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.dependencies.ViewGraph;
import com.amsen.par.cewlrency.base.event.EventBus;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.source.CurrencySource;
import com.amsen.par.cewlrency.view.CurrencyEvent;
import com.amsen.par.cewlrency.view.activity.CurrencyActivity;
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
    private EventBus<CurrencyEvent> eventBus;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        source = getViewGraph().currencySource;
        eventBus = getViewGraph().eventBus;

        currencyTextView.applyEventBus(eventBus);
        currencyEditText.applyEventBus(eventBus);
        currencyPicker.applyEventBus(eventBus);

        currencyEditText.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //Android does not allow dashed strokes on accelerated devices..

        initialState();
    }

    private void initialState() {
        source.getCurrencies(this::onCurrencies);
    }

    private void onCurrencies(List<Currency> currencies) {
        currencyPicker.applyItems(currencies);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currency, container, false);
    }

    public ViewGraph getViewGraph() {
        return ((CurrencyActivity) getActivity()).getViewGraph();
    }
}
