package com.amsen.par.cewlrency.base.util;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Currency;
import java.util.Locale;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyUtilTest {
    @Test
    public void javaCurrencyApiTest() {
        String formatted = CurrencyUtil.format(new Locale("SV"), Currency.getInstance("SEK"), 3000000.5d);

        Assert.assertEquals("SEK 3 000 000,50", formatted); //not white spaces, but the \x{A0} character (no-break space)
    }
}