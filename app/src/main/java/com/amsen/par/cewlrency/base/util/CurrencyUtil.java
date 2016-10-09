package com.amsen.par.cewlrency.base.util;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyUtil {
    private static boolean isCurrencySymbolBeforeValueInLocale = format(Currency.getInstance("SEK"), 0).indexOf("SEK") == 0;

    public static String format(Locale locale, Currency currency, double value) {
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setCurrency(currency);
        format.setMaximumFractionDigits(currency.getDefaultFractionDigits());
        format.setGroupingUsed(true);

        String formatted = format.format(value);

        if(isCurrencySymbolBeforeValueInLocale && !formatted.contains(" ")) { //ensure space in formatted to avoid "SEK123" in favor of "SEK 123" for some locales
            String symbol = formatted.substring(0, currency.getSymbol().length());
            String valueString = formatted.substring(currency.getSymbol().length(), formatted.length());

            return symbol + " " + valueString;
        }

        return formatted;
    }

    public static String format(Currency currency, double value) {
        return format(Locale.getDefault(), currency, value);
    }

    /**
     * Returns true if currency symbol is before the
     * value in the current default locale. Ex:
     *
     * SEK123 == true
     * 123SEK == false
     */
    public static boolean isCurrencySymbolBeforeValueInLocale() {
        return isCurrencySymbolBeforeValueInLocale;
    }
}
