package com.mss.imagesearcher.view.info;

import android.content.Intent;
import android.widget.TextView;

import com.mss.imagesearcher.R;
import com.mss.imagesearcher.di.RobolectricApp;
import com.mss.imagesearcher.di.RobolectricComponent;
import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.presenter.info.InfoPresenter;
import com.mss.imagesearcher.robolectric.ShadowSnackbar;
import com.mss.imagesearcher.view.detail.DetailActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, application = RobolectricApp.class, shadows = {ShadowSnackbar.class})
public class InfoActivityTest {

    @Inject
    InfoPresenter presenter;
    private InfoActivity infoActivity;

    @Before
    public void setUp() throws Exception {
        final Intent intent = new Intent(RuntimeEnvironment.systemContext, InfoActivity.class);
        intent.putExtra(DetailActivity.PARAMETER_POSITION_TAG, 1);
        infoActivity = Robolectric.buildActivity(InfoActivity.class, intent)
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
    public void showInfo() {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setWidth(1000);
        imageInfo.setHeight(2000);
        imageInfo.setViews(1);
        imageInfo.setLikes(2);
        imageInfo.setComments(3);
        imageInfo.setType("photo");
        imageInfo.setTags("tags");
        imageInfo.setPageUrl("URL");

        TextView tvSize = infoActivity.findViewById(R.id.tvSize);
        TextView tvViews = infoActivity.findViewById(R.id.tvViews);
        TextView tvLikes = infoActivity.findViewById(R.id.tvLikes);
        TextView tvComments = infoActivity.findViewById(R.id.tvComments);
        TextView tvType = infoActivity.findViewById(R.id.tvType);
        TextView tvTags = infoActivity.findViewById(R.id.tvTags);
        TextView tvUrl = infoActivity.findViewById(R.id.tvUrl);


        infoActivity.showInfo(imageInfo);


        assertEquals("2000 x 1000", tvSize.getText());
        assertEquals("1", tvViews.getText());
        assertEquals("2", tvLikes.getText());
        assertEquals("3", tvComments.getText());
        assertEquals("photo", tvType.getText());
        assertEquals("tags", tvTags.getText());
        assertEquals("URL", tvUrl.getText());
    }
}