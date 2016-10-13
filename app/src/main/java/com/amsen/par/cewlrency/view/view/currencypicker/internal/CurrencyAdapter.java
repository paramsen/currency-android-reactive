/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amsen.par.cewlrency.view.view.currencypicker.internal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.model.Currency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.SimpleViewHolder> {
    private final Context mContext;
    private List<Currency> currencies;

    public CurrencyAdapter(Context context) {
        mContext = context;
        currencies = new ArrayList<>();
        currencies.add(new Currency("INIT", 1337, java.util.Currency.getInstance(Currency.BASE_ID)));//Workaround for RecyclerPager lib & its looper
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_currency_picker_recycler_item, null, false));
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.title.setText(currencies.get(position).getId());
        holder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public void applyItems(List<Currency> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public int mPosition;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }
}
