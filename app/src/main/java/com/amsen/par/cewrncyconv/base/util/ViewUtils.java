package com.amsen.par.cewrncyconv.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.amsen.par.cewrncyconv.view.activity.CurrencyActivity;

/**
 * @author Pär Amsen 2016
 */
public class ViewUtils {
    public static int dpToPx(final Context context, final int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    /**
     * SO http://stackoverflow.com/a/17789187/5704677
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Wrote this to unpack the Context provided to Views
     * from the LayoutInflater to get the Activity context.
     *
     * Because Android sometimes wraps the Context in a ContextWrapper
     * This is recursive for ContextWrappers wrapped in ContextWrappers.
     */
    public static Activity getActivity(Context context) {
        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        } else if (context instanceof ContextWrapper && ((ContextWrapper) context).getBaseContext() instanceof Activity) {
            activity = (Activity) ((ContextWrapper) context).getBaseContext();
        } else if (context instanceof ContextWrapper && ((ContextWrapper) context).getBaseContext() instanceof ContextWrapper) {
            activity = getActivity(((ContextWrapper) context).getBaseContext());
        }

        if (activity == null) {
            getActivity(context);
        }

        return activity;
    }
}
