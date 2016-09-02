package com.amsen.par.cewlrency.view.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.CurrencyApplication;
import com.amsen.par.cewlrency.base.dependencies.AppGraph;
import com.amsen.par.cewlrency.base.dependencies.ViewGraph;
import com.amsen.par.cewlrency.view.fragment.CurrencyFragment;

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
