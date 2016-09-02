package com.amsen.par.cewlrency.base;

import android.app.Application;

import com.amsen.par.cewlrency.base.dependency.application.ApplicationComponent;
import com.amsen.par.cewlrency.base.dependency.application.ApplicationModule;
import com.amsen.par.cewlrency.base.dependency.application.DaggerApplicationComponent;
import com.amsen.par.cewlrency.base.dependency.application.SourceModule;

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
