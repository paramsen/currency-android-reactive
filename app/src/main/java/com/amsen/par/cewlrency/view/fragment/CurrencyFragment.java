package com.amsen.par.cewlrency.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.rx.RxUtils;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.base.util.ViewUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.source.CurrencySource;
import com.amsen.par.cewlrency.source.PreferencesSource;
import com.amsen.par.cewlrency.view.CurrencyEvent;
import com.amsen.par.cewlrency.view.view.CurrencyInput;
import com.amsen.par.cewlrency.view.view.CurrencyPicker;
import com.amsen.par.cewlrency.view.view.CurrencyTextView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

import static com.amsen.par.cewlrency.persistence.preferences.PreferencesHelper.CURRENCY_FROM;
import static com.amsen.par.cewlrency.persistence.preferences.PreferencesHelper.CURRENCY_TO;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyFragment extends BaseFragment {
    @Inject
    CurrencySource source;
    @Inject
    PreferencesSource preferencesSource;

    @BindView(R.id.currencyPickerFrom)
    CurrencyPicker currencyPickerFrom;
    @BindView(R.id.currencyPickerTo)
    CurrencyPicker currencyPickerTo;
    @BindView(R.id.currencyEditText)
    CurrencyInput currencyInput;
    @BindView(R.id.currencyTextView)
    CurrencyTextView currencyTextView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBaseActivity().getComponent().inject(this);
        setupViews();
        initialState();
    }

    private void setupViews() {
        currencyInput.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //Android does not allow dashed strokes on accelerated devices..
        currencyPickerFrom.setEventType(CurrencyEvent.Type.CHANGE_CURRENCY_FROM);
        currencyPickerTo.setEventType(CurrencyEvent.Type.CHANGE_CURRENCY_TO);
    }

    private void initialState() {
        source.getCurrencies()
                .map(RxUtils::sort)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SubscriberUtils.onNext(this::onCurrencies));
    }

    private void onCurrencies(List<Currency> currencies) {
        String from = preferencesSource.<String>get(CURRENCY_FROM);
        String to = preferencesSource.<String>get(CURRENCY_TO);

        currencyPickerFrom.applyItems(currencies, from != null ? from : "AUD");
        currencyPickerTo.applyItems(currencies, to != null ? to : "SEK");
    }

    @OnClick(R.id.container)
    public void onClick() {
        ViewUtils.hideKeyboard(getBaseActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currency, container, false);
    }
}
