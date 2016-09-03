package com.amsen.par.cewlrency.view.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.rx.subscriber.SubscriberUtils;
import com.amsen.par.cewlrency.base.util.CurrencyUtil;
import com.amsen.par.cewlrency.base.util.ViewUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.view.CurrencyEvent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyInput extends LinearLayout {
    @Inject
    EventStream eventStream;

    @BindView(R.id.textViewBefore)
    TextView textViewBefore;
    @BindView(R.id.textViewAfter)
    TextView textViewAfter;
    @BindView(R.id.editText)
    EditText editText;

    private Currency currency;
    private boolean currencyBefore;

    public CurrencyInput(Context context) {
        super(context);
        init(context);
    }

    public CurrencyInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurrencyInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ViewUtils.inflate(context, R.layout.view_amount_input, this, true, (activity, view) -> {
            activity.getComponent().inject(this);
            ButterKnife.bind(this);

            initialState();
            setupBehavior();
        });
    }

    private void initialState() {
        textViewBefore.setVisibility(GONE);
        textViewAfter.setVisibility(GONE);

        currencyBefore = CurrencyUtil.format(java.util.Currency.getInstance("SEK"), 0).indexOf("SEK") == 0; //whether device Locale formats currency before/after a value
    }

    private void setupBehavior() {
        streamCurrencyEvents();
        editTextListener();
    }

    private void streamCurrencyEvents() {
        eventStream.stream()
                .filter(e -> e instanceof CurrencyEvent && ((CurrencyEvent) e).type == CurrencyEvent.Type.CHANGE_CURRENCY_FROM)
                .map(e -> (Currency) ((CurrencyEvent) e).value)
                .subscribe(SubscriberUtils.onNext(this::onCurrency));
    }

    private void onCurrency(Currency currency) {
        this.currency = currency;
        textViewBefore.setText(currency.getCurrency().getSymbol() + " ");
        textViewAfter.setText(" " + currency.getCurrency().getSymbol());
    }

    private void editTextListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    try {
                        double value = Double.parseDouble(s.toString());
                        editText.setHint("0");

                        if(currencyBefore) {
                            textViewBefore.setVisibility(VISIBLE);
                            textViewAfter.setVisibility(GONE);
                        } else {
                            textViewBefore.setVisibility(GONE);
                            textViewAfter.setVisibility(VISIBLE);
                        }

                        eventStream.post(new CurrencyEvent<>(value, CurrencyEvent.Type.CHANGE_AMOUNT));
                    } catch (Exception e) {
                        //ignore
                    }
                } else {
                    editText.setHint("Amount");

                    textViewBefore.setVisibility(GONE);
                    textViewAfter.setVisibility(GONE);
                }
            }
        });
    }
}