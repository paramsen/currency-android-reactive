package com.amsen.par.cewrncyconv.view.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager that shows 3 Views at once. Encapsulates
 * all UI logic for the currency picker.
 *
 * @author Pär Amsen 2016
 */
public class CurrencyPicker extends ViewPager {
    private List<Integer> items;

    public CurrencyPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        items = new ArrayList<>();
        setOffscreenPageLimit(2);
        setAdapter(getCustomAdapter());
    }

    public void applyItems(List<Integer> items) {
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
                TextView textView = new TextView(getContext());
                textView.setText(position + "SEK");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
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
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(childAt.getMeasuredHeight(), MeasureSpec.EXACTLY); //make ViewPager wrap_content of child
            int measuredWidth = childAt.getMeasuredWidth();
            setPageMargin((int) (-measuredWidth * .666f)); // show three views at once in ViewPager
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}