package com.amsen.par.cewlrency.base.util;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyUtil {
    public static String format(Locale locale, Currency currency, double value) {
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setCurrency(currency);
        format.setMaximumFractionDigits(currency.getDefaultFractionDigits());
        format.setGroupingUsed(true);

        return format.format(value);
    }

    public static String format(Currency currency, double value) {
        return format(Locale.getDefault(), currency, value);
    }
}
