package com.amsen.par.cewlrency.view.activity;

import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.amsen.par.cewlrency.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Some small UI tests as per request.
 *
 * @author Pär Amsen 2016
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CurrencyActivityTest {
    @Rule
    public ActivityTestRule<CurrencyActivity> rule = new ActivityTestRule<CurrencyActivity>(CurrencyActivity.class);

    @Test
    public void changeAmount() throws InterruptedException {
        onView(withText(Matchers.containsString("￥0"))).check(matches(isDisplayed()));
        onView(withId(R.id.currencyEditText)).perform(ViewActions.typeText("123"));
        onView(withText(Matchers.containsString("￥0"))).check(doesNotExist());
    }

    @Test
    public void changeCurrency() {
        onView(withText(Matchers.containsString("€"))).check(doesNotExist()); //Shows YEN first
        onView(withId(R.id.viewPager)).perform(ViewActions.swipeLeft());
        onView(withText(Matchers.containsString("€"))).check(matches(isDisplayed())); //is EUR showing after swipe?
    }
}