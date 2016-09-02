package com.amsen.par.cewlrency.base.dependency.view;

import com.amsen.par.cewlrency.view.activity.BaseActivity;
import com.amsen.par.cewlrency.view.activity.CurrencyActivity;

import dagger.Subcomponent;

/**
 * @author Pär Amsen @ Nixon Nixon / 2016
 */
@Subcomponent(modules = ViewModule.class)
public interface ViewComponent {
    void inject(BaseActivity activity);
    void inject(CurrencyActivity activity);
}
