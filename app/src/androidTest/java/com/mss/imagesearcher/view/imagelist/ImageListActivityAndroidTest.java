package com.mss.imagesearcher.view.imagelist;

import android.view.KeyEvent;

import androidx.test.rule.ActivityTestRule;

import com.mss.imagesearcher.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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