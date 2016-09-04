package com.amsen.par.cewlrency.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.CurrencyApplication;
import com.amsen.par.cewlrency.base.dependency.view.ViewComponent;
import com.amsen.par.cewlrency.base.dependency.view.ViewModule;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {
    private ViewComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        _inject();
        inject();
    }

    @LayoutRes
    protected abstract int getLayout();

    private void _inject() {
        component = ((CurrencyApplication) getApplication()).getApplicationComponent().plus(new ViewModule(this));
        component.inject(this);
        ButterKnife.bind(this);
    }

    protected abstract void inject();

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public ViewComponent getComponent() {
        return component;
    }
}
