package com.amsen.par.cewlrency.base.dependency.view;

import com.amsen.par.cewlrency.view.activity.BaseActivity;
import com.amsen.par.cewlrency.view.activity.CurrencyActivity;
import com.amsen.par.cewlrency.view.fragment.BaseFragment;
import com.amsen.par.cewlrency.view.fragment.CurrencyFragment;
import com.amsen.par.cewlrency.view.view.CurrencyInput;
import com.amsen.par.cewlrency.view.view.CurrencyPickerViewPager;
import com.amsen.par.cewlrency.view.view.CurrencyTextView;
import com.amsen.par.cewlrency.view.view.FavoriteFAB;
import com.amsen.par.cewlrency.view.view.currencypicker.CurrencyPickerRecycler;

import dagger.Subcomponent;

/**
 * @author Pär Amsen @ Nixon Nixon / 2016
 */
@Subcomponent(modules = ViewModule.class)
public interface ViewComponent {
    void inject(BaseActivity activity);
    void inject(CurrencyActivity activity);

    void inject(BaseFragment fragment);
    void inject(CurrencyFragment fragment);

    void inject(CurrencyPickerViewPager view);
    void inject(CurrencyPickerRecycler view);
    void inject(CurrencyTextView view);
    void inject(CurrencyInput view);
    void inject(FavoriteFAB view);
}
