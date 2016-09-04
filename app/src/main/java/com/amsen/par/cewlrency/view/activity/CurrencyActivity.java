package com.amsen.par.cewlrency.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.rx.event.Event;
import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.persistence.preferences.PreferencesHelper;
import com.amsen.par.cewlrency.source.CurrencySource;
import com.amsen.par.cewlrency.source.PreferencesSource;
import com.amsen.par.cewlrency.view.CurrencyEvent;
import com.amsen.par.cewlrency.view.fragment.CurrencyFragment;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

import static com.amsen.par.cewlrency.persistence.preferences.PreferencesHelper.CURRENCY_FROM;
import static com.amsen.par.cewlrency.persistence.preferences.PreferencesHelper.CURRENCY_TO;

public class CurrencyActivity extends BaseActivity {
    @Inject
    CurrencySource source;
    @Inject
    PreferencesSource preferencesSource;
    @Inject
    EventStream eventStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialState();
        setupBehavior();
    }

    private void initialState() {
        source.getCurrencies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SubscriberUtils.onNext(e -> showFragment(new CurrencyFragment())));
    }

    private void setupBehavior() {
        String from = preferencesSource.<String>get(CURRENCY_FROM);
        String to = preferencesSource.<String>get(CURRENCY_TO);

        if(from == null || to == null) {
            preferencesSource.put(CURRENCY_FROM, "AUD");
            preferencesSource.put(CURRENCY_TO, "SEK");

            eventStream.stream()
                    .filter(e -> e instanceof CurrencyEvent)
                    .cast(CurrencyEvent.class)
                    .subscribe(SubscriberUtils.onNext(this::onCurrencyEvent));
        }
    }

    private void onCurrencyEvent(CurrencyEvent event) {
        if(event.type == CurrencyEvent.Type.CHANGE_CURRENCY_FROM) {
            preferencesSource.put(CURRENCY_FROM, ((Currency)event.value).getId());
        } else if(event.type == CurrencyEvent.Type.CHANGE_CURRENCY_TO) {
            preferencesSource.put(CURRENCY_TO, ((Currency)event.value).getId());
        }
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }
}
