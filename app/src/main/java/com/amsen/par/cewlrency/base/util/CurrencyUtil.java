package com.amsen.par.cewlrency.base.util;

import java.util.Locale;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyUtil {
    //CAD|EUR|GBP|JPY|USD
    public static Locale getLocale(String id) {
        switch (id) {
            case "CAD":
                return Locale.CANADA;
            case "EUR":
                return Locale.GERMANY;
            case "GBP":
                return Locale.UK;
            case "JPY":
                return Locale.JAPAN;
            case "USD":
                return Locale.US;
            default:
                return Locale.getDefault();

        }

    }

}
