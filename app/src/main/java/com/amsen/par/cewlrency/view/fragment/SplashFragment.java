package com.amsen.par.cewlrency.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.amsen.par.cewlrency.BuildConfig;
import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.base.util.ViewUtils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.amsen.par.cewlrency.base.util.ViewUtils.dpToPx;

/**
 * @author Pär Amsen 2016
 */
public class SplashFragment extends BaseFragment {
    @BindView(R.id.loader)
    View loader;
    @BindView(R.id.version)
    TextView version;

    private boolean detached;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader.setAlpha(0);

        Observable.just(true)
                .delay(1200, TimeUnit.MILLISECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SubscriberUtils.onComplete(this::showLoader));
    }

    private void showLoader() {
        if (!detached) {
            loader.animate()
                    .alpha(1)
                    .setDuration(300)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        detached = true;
    }
}
