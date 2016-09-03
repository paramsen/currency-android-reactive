package com.amsen.par.cewlrency.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.source.CurrencySource;
import com.amsen.par.cewlrency.view.fragment.CurrencyFragment;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class CurrencyActivity extends BaseActivity {
    @Inject
    CurrencySource source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialState();
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }

    private void initialState() {
        source.getCurrencies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SubscriberUtils.onNext(e -> showFragment(new CurrencyFragment())));
    }
}
