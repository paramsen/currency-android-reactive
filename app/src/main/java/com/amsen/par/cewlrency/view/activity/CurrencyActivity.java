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
    }

    private void initialState() {
        source.getCurrencies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SubscriberUtils.onNext(e -> showFragment(new CurrencyFragment())));
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }
}
