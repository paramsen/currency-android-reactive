package com.amsen.par.cewrncyconv.view.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.amsen.par.cewrncyconv.R;
import com.amsen.par.cewrncyconv.base.CurrencyApplication;
import com.amsen.par.cewrncyconv.base.dependencies.AppGraph;
import com.amsen.par.cewrncyconv.base.dependencies.ViewGraph;
import com.amsen.par.cewrncyconv.view.fragment.CurrencyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyActivity extends AppCompatActivity {
    private ViewGraph viewGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewGraph = new ViewGraph(getAppGraph().currencySource);
        
        initialState();
    }

    private void initialState() {
        viewGraph.currencySource.getCurrencies(e -> showFragment(new CurrencyFragment()));
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragments, fragment)
                .commit();
    }

    private AppGraph getAppGraph() {
        return ((CurrencyApplication) getApplication()).getAppGraph();
    }

    public ViewGraph getViewGraph() {
        return viewGraph;
    }
}
