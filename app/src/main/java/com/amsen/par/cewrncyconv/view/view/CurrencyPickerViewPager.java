package com.amsen.par.cewrncyconv.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amsen.par.cewrncyconv.R;
import com.amsen.par.cewrncyconv.base.event.EventBus;
import com.amsen.par.cewrncyconv.base.util.ViewUtils;
import com.amsen.par.cewrncyconv.model.Currency;
import com.amsen.par.cewrncyconv.view.CurrencyEvent;

import java.util.ArrayList;
import java.util.List;

import static com.amsen.par.cewrncyconv.base.util.ViewUtils.dpToPx;

/**
 * ViewPager that shows 3 Views at once. Encapsulates
 * all UI logic for the currency picker.
 *
 * @author Pär Amsen 2016
 */
public class CurrencyPickerViewPager extends ViewPager {
    private List<Currency> items;
    private EventBus<CurrencyEvent> eventBus;

    public CurrencyPickerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        items = new ArrayList<>();
        setClipChildren(false);
        setOffscreenPageLimit(2);
        setAdapter(getCustomAdapter());
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                eventBus.post(new CurrencyEvent<>(items.get(position).getId()));
            }
        });
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

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}