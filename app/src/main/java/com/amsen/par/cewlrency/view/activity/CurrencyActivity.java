package com.amsen.par.cewlrency.view.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.analytics.BehaviorTracker;
import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.source.CurrencySource;
import com.amsen.par.cewlrency.view.fragment.CurrencyFragment;
import com.amsen.par.cewlrency.view.fragment.SplashFragment;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;

public class CurrencyActivity extends BaseActivity {
    @Inject
    CurrencySource source;
    @Inject
    EventStream eventStream;
    @Inject
    BehaviorTracker behaviorTracker;

    @BindView(android.R.id.content)
    View content;
    @BindView(R.id.fragmentContainer)
    View fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(new SplashFragment());

        initialState();
        analytics();
    }

    private void initialState() {
        source.getCurrencies()
                .delay(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SubscriberUtils.onNextOnError(e -> showFragmentTransitionIcon(new CurrencyFragment(), fragmentContainer.findViewById(R.id.icon)), this::onNetworkError));
    }

    private void onNetworkError(Throwable throwable) {
        Snackbar.make(content, "No internet connection", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", e -> {
                    initialState();
                })
                .show();
    }

    private void analytics() {
        behaviorTracker.start();
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected View getRootView() {
        return fragmentContainer;
    }
}
