package com.mss.instamat.view.imagelist;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import com.mss.instamat.R;
import com.mss.instamat.di.RobolectricApp;
import com.mss.instamat.di.RobolectricComponent;
import com.mss.instamat.domain.ImageListModel;
import com.mss.instamat.domain.models.ImageInfo;
import com.mss.instamat.presenter.imagelist.IRvImageListPresenter;
import com.mss.instamat.presenter.imagelist.ImageListPresenter;
import com.mss.instamat.robolectric.ShadowSnackbar;
import com.mss.instamat.view.detail.DetailActivity;
import com.mss.instamat.view.helpers.ImageLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, application = RobolectricApp.class, shadows = {ShadowSnackbar.class})
public class ImageListActivityTest {

    private ImageListActivity imageListActivity;
    private List<ImageInfo> imageInfoList;

    @Inject
    ImageListPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    @Before
    public void setUp() {
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
        initImageInfoList();
        ImageListPresenter.RvPresenter rvPresenter = mock(ImageListPresenter.RvPresenter.class);
        when(presenter.getRvPresenter()).thenReturn(rvPresenter);
        when(rvPresenter.getItemCount()).thenReturn(imageInfoList.size());

        imageListActivity.refreshImageList();
        imageListActivity.refreshImageList();

        verify(presenter).getRvPresenter();
    }

    @Test
    public void showNotFoundMessage_showSnackbar() {
        ShadowSnackbar.reset();

        imageListActivity.showNotFoundMessage();

        assertEquals(ShadowSnackbar.getTextOfLatestSnackbar(), imageListActivity.getString(R.string.not_found_message));
    }

    @Test
    public void openDetailActivity_startDetailActivityWithParam() {
        imageListActivity.openDetailActivity(1);

        Intent intent = Shadows.shadowOf(imageListActivity).getNextStartedActivity();
        assertEquals("com.mss.instamat.view.detail.DetailActivity",  intent.getComponent().getClassName());
        assertEquals(1, intent.getIntExtra(DetailActivity.PARAMETER_POSITION_TAG,0));
    }

    private void initImageInfoList() {
        imageInfoList = new ArrayList();
        for (int i = 0; i < 50; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setId(i);
            imageInfo.setPreviewURL("https:\\\\previewurl" + i);
            imageInfo.setLargeImageURL("https:\\\\largeimagewurl" + i);
            imageInfoList.add(imageInfo);
        }
    }
}