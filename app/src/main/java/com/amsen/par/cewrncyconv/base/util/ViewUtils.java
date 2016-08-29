package com.amsen.par.cewrncyconv.base.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author Pär Amsen 2016
 */
public class ViewUtils {
    public static int dpToPx(final Context context, final int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
