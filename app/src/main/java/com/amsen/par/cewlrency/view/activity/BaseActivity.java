package com.amsen.par.cewlrency.view.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.CurrencyApplication;
import com.amsen.par.cewlrency.base.dependency.view.ViewComponent;
import com.amsen.par.cewlrency.base.dependency.view.ViewModule;
import com.amsen.par.cewlrency.base.rx.event.EventStream;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    EventStream eventStream;
    private ViewComponent component;
    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener;
    private Window window;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayout());

        _inject();
        inject();

        window = getWindow();
        rootView = window.findViewById(Window.ID_ANDROID_CONTENT);
        keyboardLayoutListener = getKeyboardLayoutListener();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
    }

    @LayoutRes
    protected abstract int getLayout();

    protected abstract View getRootView();

    private void _inject() {
        component = ((CurrencyApplication) getApplication()).getApplicationComponent().plus(new ViewModule(this));
        component.inject(this);
        ButterKnife.bind(this);
    }

    protected abstract void inject();

    public void showFragment(Fragment fragment) {
        fragment.setEnterTransition(new Fade());
        fragment.setExitTransition(new Fade());

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    public void showFragmentTransitionIcon(Fragment fragment, View shared) {
        Transition changeTransform = TransitionInflater.from(this).
                inflateTransition(R.transition.change_image_transform);
        fragment.setSharedElementEnterTransition(changeTransform);
        fragment.setEnterTransition(new Fade());

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .addSharedElement(shared, "icon")
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    public ViewTreeObserver.OnGlobalLayoutListener getKeyboardLayoutListener() {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            int initialHeight;

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                View view = window.getDecorView();
                view.getWindowVisibleDisplayFrame(r);

                if (initialHeight == 0) {
                    initialHeight = r.height();
                } else {
                    if (initialHeight > r.height()) {
                        eventStream.post(KeyboardEvent.KEYBOARD_SHOW);
                    } else if (initialHeight == r.height()) {
                        eventStream.post(KeyboardEvent.KEYBOARD_HIDE);
                    }
                }
            }
        };
    }

    public ViewComponent getComponent() {
        return component;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
    }
}
