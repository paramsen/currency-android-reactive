package com.amsen.par.cewrncyconv.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.amsen.par.cewrncyconv.R;
import com.amsen.par.cewrncyconv.base.dependencies.ViewGraph;
import com.amsen.par.cewrncyconv.base.event.EventBus;
import com.amsen.par.cewrncyconv.model.Currency;
import com.amsen.par.cewrncyconv.source.CurrencySource;
import com.amsen.par.cewrncyconv.view.activity.CurrencyActivity;
import com.amsen.par.cewrncyconv.view.view.CurrencyPicker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyFragment extends Fragment {
    @BindView(R.id.currencyPicker)
    CurrencyPicker currencyPicker;
    @BindView(R.id.currencyInput)
    EditText currencyInput;

    private CurrencySource source;
    private EventBus eventBus;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        source = getViewGraph().currencySource;
        eventBus = getViewGraph().eventBus;

        currencyPicker.setEventBus(eventBus);
        currencyInput.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //Android does not allow dashed strokes on accelerated devices..

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
