package com.mss.instamat.view.detail;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.mss.instamat.R;
import com.mss.instamat.di.RobolectricApp;
import com.mss.instamat.di.RobolectricComponent;
import com.mss.instamat.presenter.detail.DetailPresenter;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, application = RobolectricApp.class)
public class DetailActivityTest {

    private DetailActivity detailActivity;

    @Inject
    DetailPresenter presenter;

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
    public void onMenuSaveClick_callPresenterOnCreateWithArgument() {
        ShadowApplication application = new ShadowApplication();
        application.grantPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ShadowActivity shadowActivity = Shadows.shadowOf(detailActivity);
        ImageView imageView = detailActivity.findViewById(R.id.imageView);
        imageView.setImageDrawable(new BitmapDrawable());

        shadowActivity.clickMenuItem(R.id.miSave);

        verify(presenter).onSaveClick(eq(1), any());
    }

    @Test
    public void onMenuShareClick_callPresenterOnCreateWithArgument() {
        ShadowApplication application = new ShadowApplication();
        application.grantPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ShadowActivity shadowActivity = Shadows.shadowOf(detailActivity);
        ImageView imageView = detailActivity.findViewById(R.id.imageView);
        imageView.setImageDrawable(new BitmapDrawable());

        shadowActivity.clickMenuItem(R.id.miShare);

        verify(presenter).onShareClick(eq(1), any());
    }
}