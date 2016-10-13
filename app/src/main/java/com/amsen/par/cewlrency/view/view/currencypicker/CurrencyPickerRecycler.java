package com.amsen.par.cewlrency.view.view.currencypicker;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.util.ViewUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.view.CurrencyEvent;
import com.amsen.par.cewlrency.view.activity.BaseActivity;
import com.amsen.par.cewlrency.view.view.currencypicker.internal.CurrencyAdapter;
import com.amsen.par.cewlrency.view.view.currencypicker.internal.LoopRecyclerViewPager;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Pär Amsen 2016
 */
public class CurrencyPickerRecycler extends LoopRecyclerViewPager {
    @Inject
    EventStream eventStream;

    private CurrencyAdapter adapter;
    private BaseActivity activity;
    private List<Currency> items;
    private CurrencyEvent.Type eventType;

    public CurrencyPickerRecycler(Context context) {
        super(context);
        init(context);
    }

    public CurrencyPickerRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurrencyPickerRecycler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            activity = ViewUtils.getBaseActivity(context);
            activity.getComponent().inject(this);
        }

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(layout);
        setHasFixedSize(true);
        setLongClickable(true);

        adapter = new CurrencyAdapter(getContext());
        setAdapter(adapter);

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastPosition = -1;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                ViewUtils.hideKeyboard(activity);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int currentPosition = getActualCurrentPosition();

                if (currentPosition != lastPosition) {
                    if (items != null && items.size() > 0 && currentPosition != Integer.MAX_VALUE) {
                        int actualPosition = transformToActualPosition(currentPosition);

                        if (actualPosition < 0) {
                            actualPosition = items.size() + actualPosition;
                        }

                        eventStream.post(new CurrencyEvent<>(items.get(actualPosition), eventType));
                    }
                }

                lastPosition = currentPosition;

                int childCount = getChildCount();
                int width = getChildAt(0).getWidth();
                int padding = (getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    float rate = 0;
                    ;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.3f);
                        v.setScaleX(1 - rate * 0.3f);

                    } else {
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.7f + rate * 0.3f);
                        v.setScaleX(0.7f + rate * 0.3f);
                    }
                }
            }
        });

        addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (getChildCount() < 3) {
                if (getChildAt(1) != null) {
                    if (getCurrentPosition() == 0) {
                        View v1 = getChildAt(1);
                        v1.setScaleY(0.7f);
                        v1.setScaleX(0.7f);
                    } else {
                        View v1 = getChildAt(0);
                        v1.setScaleY(0.7f);
                        v1.setScaleX(0.7f);
                    }
                }
            } else {
                if (getChildAt(0) != null) {
                    View v0 = getChildAt(0);
                    v0.setScaleY(0.7f);
                    v0.setScaleX(0.7f);
                }
                if (getChildAt(2) != null) {
                    View v2 = getChildAt(2);
                    v2.setScaleY(0.7f);
                    v2.setScaleX(0.7f);
                }
            }

        });
    }

    public void applyItems(List<Currency> items, String initialCurrencyId) {
        this.items = items;
        adapter.applyItems(items);
        firstItemsApply();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(initialCurrencyId)) {
                if (i == 0) { //if first index, onPageSelected listener won't get called
                    eventStream.post(new CurrencyEvent<>(items.get(i), eventType));
                }

                smoothScrollToPosition(i);

                break;
            }
        }
    }

    public void setEventType(CurrencyEvent.Type eventType) {
        this.eventType = eventType;
    }
}
