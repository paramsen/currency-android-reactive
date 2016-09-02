package com.amsen.par.cewlrency.view.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.view.CurrencyEvent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyEditText extends EditText {
    private EventStream eventStream;

    public CurrencyEditText(Context context) {
        super(context);
        init();
    }

    public CurrencyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable view) {
                double value = 0;
                try {
                    value = Double.parseDouble(view.toString().replaceAll("\\$|" + DecimalFormatSymbols.getInstance().getGroupingSeparator(), ""));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                eventStream.post(new CurrencyEvent<>(value, CurrencyEvent.Type.CHANGE_AMOUNT));

                try {
                    String formatted = DecimalFormat.getInstance().format(value);

                    if (!formatted.equals(view.toString().substring(1))) {
                        setText("$" + formatted);
                        setSelection(getText().toString().length());
                    }
                } catch (NumberFormatException e) {
                }
            }
        });
    }
}
