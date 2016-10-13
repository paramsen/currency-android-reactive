package com.amsen.par.cewlrency.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.rx.RxUtils;
import com.amsen.par.cewlrency.base.rx.event.Event;
import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.util.ViewUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.source.CurrencySource;
import com.amsen.par.cewlrency.source.PreferencesSource;
import com.amsen.par.cewlrency.view.CurrencyEvent;
import com.amsen.par.cewlrency.view.activity.KeyboardEvent;
import com.amsen.par.cewlrency.view.view.CurrencyInput;
import com.amsen.par.cewlrency.view.view.CurrencyPicker;
import com.amsen.par.cewlrency.view.view.CurrencyTextView;
import com.amsen.par.cewlrency.view.view.FavoriteFAB;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils.onNext;
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
    @Inject
    EventStream eventStream;

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.currencyPickerFrom)
    CurrencyPicker currencyPickerFrom;
    @BindView(R.id.currencyPickerTo)
    CurrencyPicker currencyPickerTo;
    @BindView(R.id.currencyEditText)
    CurrencyInput currencyInput;
    @BindView(R.id.currencyTextView)
    CurrencyTextView currencyTextView;
    @BindView(R.id.favFAB)
    FavoriteFAB favFAB;

    private static int debugClicks = 0;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBaseActivity().getComponent().inject(this);
        setupViews();
        setupBehavior();
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
                .subscribe(onNext(this::onCurrencies));
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

    @OnClick(R.id.icon)
    public void onClickIcon() {
        ++debugClicks;

        if(debugClicks >= 10) {
            new DebugDialogFragment().show(getFragmentManager(), DebugDialogFragment.class.getName());
        }

    }

    private void setupBehavior() {
        Observable<Event> show = eventStream.stream()
                .filter(event -> event == KeyboardEvent.KEYBOARD_SHOW)
                .share();

        Observable<Event> hide = eventStream.stream()
                .filter(event -> event == KeyboardEvent.KEYBOARD_HIDE);

        show.delay(100, TimeUnit.MILLISECONDS, Schedulers.computation())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext(e -> scrollView.smoothScrollTo(0, scrollView.getChildAt(0).getBottom())));

        show.subscribe(onNext(e -> favFAB.hide()));
        hide.subscribe(onNext(e -> favFAB.show()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currency, container, false);
    }
}
