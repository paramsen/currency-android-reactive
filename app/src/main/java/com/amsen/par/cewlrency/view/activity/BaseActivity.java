package com.amsen.par.cewlrency.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.CurrencyApplication;
import com.amsen.par.cewlrency.base.dependency.view.ViewComponent;
import com.amsen.par.cewlrency.base.dependency.view.ViewModule;

public abstract class BaseActivity extends AppCompatActivity {
    private ViewComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _inject();
        inject();
    }

    private void _inject() {
        component = ((CurrencyApplication) getApplication()).getApplicationComponent().plus(new ViewModule(this));
        component.inject(this);
    }

    public abstract void inject();

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragments, fragment)
                .commit();
    }
}
