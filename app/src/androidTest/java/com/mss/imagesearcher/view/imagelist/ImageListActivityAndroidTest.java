package com.mss.imagesearcher.view.imagelist;

import android.support.test.rule.ActivityTestRule;
import android.view.KeyEvent;

import com.mss.instamat.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class ImageListActivityAndroidTest {

    @Rule
    public ActivityTestRule<ImageListActivity> mActivityRule = new ActivityTestRule<>(
            ImageListActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onSearchAndDetail_loadImagesAndShowDetailImage() {
        onView(withId(R.id.etSearch)).perform(typeText("one"));
        onView(withId(R.id.etSearch)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.rvImages)).perform(actionOnItemAtPosition(1, click()));
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
    }
}