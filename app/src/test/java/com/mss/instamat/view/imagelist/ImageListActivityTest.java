package com.mss.instamat.view.imagelist;

import android.Manifest;
import android.view.View;
import android.widget.ProgressBar;

import com.mss.instamat.R;
import com.mss.instamat.di.RobolectricApp;
import com.mss.instamat.di.RobolectricComponent;
import com.mss.instamat.presenter.imagelist.ImageListPresenter;
import com.mss.instamat.robolectric.ShadowSnackbar;
import com.mss.instamat.view.detail.DetailActivity;
import com.mss.instamat.view.helpers.ImageLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, application = RobolectricApp.class, shadows = {ShadowSnackbar.class})
public class ImageListActivityTest {

    private ImageListActivity imageListActivity;

    @Inject
    ImageListPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    @Before
    public void setUp() throws Exception {
        imageListActivity = Robolectric.buildActivity(ImageListActivity.class)
                .create()
                .start()
                .resume()
                .visible()
                .get();
        ((RobolectricComponent) RobolectricApp.getAppComponent()).inject(this);
    }

    @Test
    public void showProgress_Hide_pbDetailHide() {
        ProgressBar progressBar = imageListActivity.findViewById(R.id.pbList);

        imageListActivity.showProgress(false);

        assertEquals(View.INVISIBLE, progressBar.getVisibility());
    }

    @Test
    public void showProgress_Show_pbDetailShow() {
        ProgressBar progressBar = imageListActivity.findViewById(R.id.pbList);

        imageListActivity.showProgress(true);

        assertEquals(View.VISIBLE, progressBar.getVisibility());
    }

    @Test
    public void refreshImageList_callPresenterGetRvPresenter() {
        imageListActivity.refreshImageList();

        verify(presenter).getRvPresenter();
    }
}