package com.amsen.par.cewlrency.view.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amsen.par.cewlrency.base.rx.event.EventStream;
import com.amsen.par.cewlrency.base.util.ViewUtils;
import com.amsen.par.cewlrency.model.Currency;
import com.amsen.par.cewlrency.view.CurrencyEvent;
import com.amsen.par.cewlrency.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.amsen.par.cewlrency.view.CurrencyEvent.Type.CHANGE_CURRENCY;

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

    public CurrencyPickerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = ViewUtils.getBaseActivity(context);
        activity.getComponent().inject(this);

        items = new ArrayList<>();
        setClipChildren(false);
        setOffscreenPageLimit(4);
        setAdapter(getCustomAdapter());
        setPageTransformer(true, getPageTransformer());
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                eventStream.post(new CurrencyEvent<>(items.get(position), CHANGE_CURRENCY));
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

    public void applyItems(List<Currency> items) {
        this.items = items;
        getAdapter().notifyDataSetChanged();
        setCurrentItem(items.size() / 2);
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
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(childAt.getMeasuredHeight() + getPaddingBottom() + getPaddingTop() , MeasureSpec.EXACTLY); //make ViewPager wrap_content of child
            int measuredWidth = childAt.getMeasuredWidth();
            setPageMargin((int) (-measuredWidth * .666f)); // show three views at once in ViewPager
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}