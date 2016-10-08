package com.amsen.par.cewlrency.view.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amsen.par.cewlrency.R;
import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.util.ViewUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.view.CurrencyEvent;
import com.amsen.par.cewlrency.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * ViewPager that shows 3 Views at once. Encapsulates
 * all UI logic for the currency picker.
 *
 * @author Pär Amsen 2016
 */
public class CurrencyPickerViewPager extends ViewPager {
    @Inject
    EventStream eventStream;

    private List<Currency> items;
    private BaseActivity activity;
    private CurrencyEvent.Type eventType;

    public CurrencyPickerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            activity = ViewUtils.getBaseActivity(context);
            activity.getComponent().inject(this);

            items = new ArrayList<>();
            setClipChildren(false);
            setOffscreenPageLimit(4);
            setAdapter(getCustomAdapter());
            setPageTransformer(true, getPageTransformer());

            setupBehavior();
        }
    }

    private void setupBehavior() {
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                eventStream.post(new CurrencyEvent<>(items.get(position), eventType));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                ViewUtils.hideKeyboard(activity);
            }
        });
    }

    private PageTransformer getPageTransformer() {
        return (page, position) -> page.setAlpha(Math.min((-Math.abs(position) * 1.2f) + 1, 1f));
    }

    public void setEventType(CurrencyEvent.Type eventType) {
        this.eventType = eventType;
    }

    public void applyItems(List<Currency> items, String initialCurrencyId) {
        this.items = items;
        getAdapter().notifyDataSetChanged();
        requestLayout();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(initialCurrencyId)) {
                if (i == 0) { //if first index, onPageSelected listener won't get called
                    eventStream.post(new CurrencyEvent<>(items.get(i), eventType));
                }

                setCurrentItem(i);

                break;
            }
        }
    }

    private PagerAdapter getCustomAdapter() {
        return new PagerAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return (int) view.getTag() == (int) object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                TextView textView = new TextView(container.getContext());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
                textView.setTextColor(ContextCompat.getColor(container.getContext(), android.R.color.white));
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                textView.setText(items.get(position).getId());
                textView.setTag(position);
                container.addView(textView);

                return position;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(container.findViewWithTag(position));
            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View childAt = getChildAt(0);

        if (childAt != null) {
            childAt.measure(widthMeasureSpec, heightMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(childAt.getMeasuredHeight() + getPaddingBottom() + getPaddingTop(), MeasureSpec.EXACTLY); //make ViewPager wrap_content of child
            int measuredWidth = childAt.getMeasuredWidth();
            setPageMargin((int) (-measuredWidth * .666f)); // show three views at once in ViewPager
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}