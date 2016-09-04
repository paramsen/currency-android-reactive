package com.amsen.par.cewlrency.base;

import android.app.Application;
import android.content.Context;

import com.amsen.par.cewlrency.base.dependency.application.ApplicationComponent;
import com.amsen.par.cewlrency.base.dependency.application.ApplicationModule;
import com.amsen.par.cewlrency.base.dependency.application.DaggerApplicationComponent;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
