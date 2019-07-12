package com.mss.instamat.view.detail;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mss.instamat.R;
import com.mss.instamat.di.RobolectricApp;
import com.mss.instamat.di.RobolectricComponent;
import com.mss.instamat.presenter.detail.DetailPresenter;
import com.mss.instamat.robolectric.ShadowSnackbar;
import com.mss.instamat.view.helpers.ImageLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, application = RobolectricApp.class, shadows = {ShadowSnackbar.class})
public class DetailActivityTest {

    private DetailActivity detailActivity;

    @Inject
    DetailPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    @Before
    public void setUp() {
        final Intent intent = new Intent(RuntimeEnvironment.systemContext, DetailActivity.class);
        intent.putExtra(DetailActivity.PARAMETER_POSITION_TAG, 1);
        detailActivity = Robolectric.buildActivity(DetailActivity.class, intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();
        ((RobolectricComponent) RobolectricApp.getAppComponent()).inject(this);
    }

    @Test
    public void onCreate_callPresenterOnCreateWithArgument() {
        verify(presenter).onCreate(1);
    }

    @Test
    public void onMenuSaveClick_HasPermission_callPresenterOnCSaveClick() {
        ShadowApplication application = new ShadowApplication();
        application.grantPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ShadowActivity shadowActivity = Shadows.shadowOf(detailActivity);
        ImageView imageView = detailActivity.findViewById(R.id.imageView);
        imageView.setImageDrawable(new BitmapDrawable());

        shadowActivity.clickMenuItem(R.id.miSave);

        verify(presenter).onSaveClick(eq(1), any());
    }

    @Test
    public void onMenuShareClick_callPresenterOnShareClick() {
        ShadowApplication application = new ShadowApplication();
        application.grantPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ShadowActivity shadowActivity = Shadows.shadowOf(detailActivity);
        ImageView imageView = detailActivity.findViewById(R.id.imageView);
        imageView.setImageDrawable(new BitmapDrawable());

        shadowActivity.clickMenuItem(R.id.miShare);

        verify(presenter).onShareClick(eq(1), any());
    }

    @Test
    public void onShowSuccessSaveMessage_showSnackbar() {
        ShadowSnackbar.reset();

        detailActivity.showSuccessSaveMessage();

        assertEquals(ShadowSnackbar.getTextOfLatestSnackbar(), detailActivity.getString(R.string.image_saved_message));
    }

    @Test
    public void onShowFailSaveMessage_showSnackbar() {
        ShadowSnackbar.reset();

        detailActivity.showFailedSaveMessage();

        assertEquals(ShadowSnackbar.getTextOfLatestSnackbar(), detailActivity.getString(R.string.image_failed_message));
    }

    @Test
    public void shareImage_startIntentChooserImageWithPath() {
        detailActivity.shareImage("path");

        Intent intent = Shadows.shadowOf(detailActivity).getNextStartedActivity();
        assertEquals(Intent.ACTION_CHOOSER, intent.getAction());
        Intent intent1 = (Intent) intent.getExtras().get(Intent.EXTRA_INTENT);
        assertEquals(Intent.ACTION_SEND, intent1.getAction());
        assertEquals("image/jpeg", intent1.getType());
        assertEquals("path", intent1.getExtras().get(Intent.EXTRA_STREAM).toString());
    }

    @Test
    public void showProgress_Hide_pbDetailHide() {
        ProgressBar progressBar = detailActivity.findViewById(R.id.pbDetail);

        detailActivity.showProgress(false);

        assertEquals(View.GONE, progressBar.getVisibility());
    }

    @Test
    public void showProgress_Show_pbDetailShow() {
        ProgressBar progressBar = detailActivity.findViewById(R.id.pbDetail);

        detailActivity.showProgress(true);

        assertEquals(View.VISIBLE, progressBar.getVisibility());
    }

    @Test
    public void showImage_SuccessLoad_callPresenterOnImageLoaded() {

        doAnswer(invocation -> {
            ((Runnable) invocation.getArguments()[3]).run();
            return null;
        }).when(imageLoader).load(any(), any(), any(), any(), any());

        detailActivity.startLoadImage("path");

        verify(presenter).onImageLoaded();
    }

    @Test
    public void showImage_FailLoad_callPresenterOnImageLoaded() {

        doAnswer(invocation -> {
            ((Runnable) invocation.getArguments()[4]).run();
            return null;
        }).when(imageLoader).load(any(), any(), any(), any(), any());

        detailActivity.startLoadImage("path");

        verify(presenter).onImageLoadFailed();
    }

    @Test
    public void onMenuSaveClick_NoPermission_noCallPresenterOnSaveClick() {
        ShadowActivity shadowActivity = Shadows.shadowOf(detailActivity);

        shadowActivity.clickMenuItem(R.id.miSave);

        verify(presenter, times(0)).onSaveClick(eq(1), any());
    }

    @Test
    public void onMenuShareClick_NoPermission_noCallPresenterOnShareClick() {
        ShadowActivity shadowActivity = Shadows.shadowOf(detailActivity);

        shadowActivity.clickMenuItem(R.id.miSave);

        verify(presenter, times(0)).onShareClick(eq(1), any());
    }
}