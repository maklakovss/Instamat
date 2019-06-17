package com.mss.instamat.view.detail;

import com.mss.instamat.di.RobolectricApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, application = RobolectricApp.class)
public class DetailActivityTest {

    private DetailActivity detailActivity;

    @Before
    public void setUp() throws Exception {
        detailActivity = Robolectric.setupActivity(DetailActivity.class);
    }

    @Test
    public void test() {

    }
}