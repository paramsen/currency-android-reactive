package com.amsen.par.cewlrency.base.dependency.view;

import com.amsen.par.cewlrency.view.activity.BaseActivity;
import com.amsen.par.cewlrency.view.activity.CurrencyActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

import dagger.Module;
import dagger.Provides;

/**
 * @author Pär Amsen @ Nixon Nixon / 2016
 */
@Module
public class ViewModule {
    private BaseActivity activity;

    public ViewModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    public BaseActivity provideActivity() {
        return activity;
    }

    @Provides
    public FirebaseAnalytics provideFirebaseAnalytics() {
        return FirebaseAnalytics.getInstance(activity);
    }
}
