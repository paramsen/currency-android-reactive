package com.amsen.par.cewrncyconv.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amsen.par.cewrncyconv.R;
import com.amsen.par.cewrncyconv.view.view.CurrencyPicker;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyFragment extends Fragment {
    @BindView(R.id.currencyPicker)
    CurrencyPicker currencyPicker;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        currencyPicker.applyItems(Arrays.asList(0, 1, 2, 3, 4));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currency, container, false);
    }
}
