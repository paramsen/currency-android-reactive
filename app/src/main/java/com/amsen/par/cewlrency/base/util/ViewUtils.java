package com.amsen.par.cewlrency.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.amsen.par.cewlrency.view.activity.BaseActivity;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;

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
     * SO http://stackoverflow.com/a/17789187/5704677
     */
    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.showSoftInput(view, 0);
    }

    /**
     * Wrote this to unpacks the Context provided to Views
     * from the LayoutInflater to get the Activity context.
     *
     * Because Android sometimes wraps the Activity in a ContextWrapper
     * This is recursive for ContextWrappers wrapped in ContextWrappers.
     */
    public static BaseActivity getBaseActivity(Context context) {
        BaseActivity activity = null;

        if (context instanceof BaseActivity) {
            activity = (BaseActivity) context;
        } else if (context instanceof ContextWrapper && ((ContextWrapper) context).getBaseContext() instanceof BaseActivity) {
            activity = (BaseActivity) ((ContextWrapper) context).getBaseContext();
        } else if (context instanceof ContextWrapper && ((ContextWrapper) context).getBaseContext() instanceof ContextWrapper) {
            activity = getBaseActivity(((ContextWrapper) context).getBaseContext());
        }

        if (activity == null) {
            getBaseActivity(context);
        }

        return activity;
    }

    /**
     * Functional version of LayoutInflater.inflate that
     * provides the caller with the BaseActivity context.
     *
     * (Views cannot normally access its owning Activity
     * which makes it hard for Views to access
     * dependencies, like getting injected by Dagger.
     * This method unwraps the provided Context
     * to find the owning Activity and supplies it to the
     * supplied function)
     *
     * Supplied function will not get called if in Android
     * Studio preview mode.
     */
    public static void inflate(Context context, @LayoutRes int layout, ViewGroup root, boolean attachToRoot, Action1<BaseActivity> onInflated) {
        if (!root.isInEditMode()) {
            BaseActivity activity = ViewUtils.getBaseActivity(context);
            LayoutInflater.from(context).inflate(layout, root, attachToRoot);

            onInflated.call(activity);
        } else {
            //Only for Android Studio preview
            LayoutInflater.from(context).inflate(layout, root, true);
        }
    }

    public static void inflate(Context context, @LayoutRes int layout, ViewGroup root, boolean attachToRoot, Action2<BaseActivity, View> onInflated) {
        if (!root.isInEditMode()) {
            BaseActivity activity = ViewUtils.getBaseActivity(context);
            View inflate = LayoutInflater.from(context).inflate(layout, root, attachToRoot);

            onInflated.call(activity, inflate);
        } else {
            //Only for Android Studio preview
            LayoutInflater.from(context).inflate(layout, root, true);
        }
    }

    public static void inflate(Context context, @LayoutRes int layout, ViewGroup root, boolean attachToRoot, Action1<BaseActivity> onInflated, Action0 onEditMode) {
        if (!root.isInEditMode()) {
            BaseActivity activity = ViewUtils.getBaseActivity(context);
            LayoutInflater.from(context).inflate(layout, root, attachToRoot);

            onInflated.call(activity);
        } else {
            //Only for Android Studio preview
            onEditMode.call();
            LayoutInflater.from(context).inflate(layout, root, true);
        }
    }
}
