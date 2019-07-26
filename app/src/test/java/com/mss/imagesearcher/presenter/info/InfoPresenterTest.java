package com.mss.imagesearcher.presenter.info;

import com.mss.imagesearcher.domain.ImageListModel;
import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.view.info.InfoView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InfoPresenterTest {

    @Mock
    private InfoView infoView;

    @Mock
    private ImageListModel model;

    private InfoPresenter infoPresenter;
    private List<ImageInfo> imageInfoList;


    @Before
    public void setUp() throws Exception {
        infoPresenter = spy(new InfoPresenter(model));
        infoPresenter.attachView(infoView);
    }

    private void initImageInfoList() {
        imageInfoList = new ArrayList();
        for (int i = 0; i < 50; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setId(i);
            imageInfo.setWebFormatURL("https:\\\\previewurl" + i);
            imageInfo.setLargeImageURL("https:\\\\largeimagewurl" + i);
            imageInfoList.add(imageInfo);
        }

        when(model.getImages()).thenReturn(imageInfoList);
    }

    @Test
    public void onCreate_InitAdMobAndShowInfo() {
        initImageInfoList();

        infoPresenter.onCreate(1);

        verify(infoView).initAdMob();
        verify(infoView).showInfo(imageInfoList.get(1));
    }

    @Test
    public void onAdLoaded() {
        initImageInfoList();

        infoPresenter.onAdLoaded();

        verify(infoView).showFullScreenAd();
    }
}