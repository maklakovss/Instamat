package com.mss.instamat.presenter.detail;

import com.mss.instamat.domain.ImageListModel;
import com.mss.instamat.domain.models.ImageInfo;
import com.mss.instamat.view.detail.DetailView;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

    @Mock
    private DetailView detailView;

    @Mock
    private ImageListModel model;

    private DetailPresenter detailPresenter;
    private List<ImageInfo> imageInfoList;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        detailPresenter = Mockito.spy(new DetailPresenter(model));

        imageInfoList = new ArrayList();
        for (int i = 0; i < 2; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setId(i);
            imageInfo.setPreviewURL("https:\\\\previewurl" + i);
            imageInfo.setLargeImageURL("https:\\\\largeimagewurl" + i);
            imageInfoList.add(imageInfo);
        }

        Mockito.when(model.getImages()).thenReturn(imageInfoList);
        detailPresenter.attachView(detailView);
    }

    @Test
    public void onCreate_isCorrect() {
        detailPresenter.onCreate(1);
        Mockito.verify(detailView).showProgress(true);
        Mockito.verify(detailView).showImage(imageInfoList.get(1).getLargeImageURL());
    }

    @Test
    public void onImageLoaded_isCorrect() {
        detailPresenter.onImageLoaded();
        Mockito.verify(detailView).showProgress(false);
    }

    @Test
    public void onImageLoadFailed_isCorrect() {
        detailPresenter.onImageLoadFailed();
        Mockito.verify(detailView).showProgress(false);
    }

    @Test
    public void onSaveClick_isCorrect() throws IOException {
        detailPresenter.onSaveClick(1, null);
        Mockito.verify(model).saveBitmap(imageInfoList.get(1), null);
        Mockito.verify(detailView).showSuccessSaveMessage();
    }

    @Test
    public void onSaveClick_throwIOException() throws IOException {
        Mockito.when(model.saveBitmap(Mockito.any(), Mockito.any())).thenThrow(new IOException());
        detailPresenter.onSaveClick(1, null);
        Mockito.verify(model).saveBitmap(imageInfoList.get(1), null);
        Mockito.verify(detailView).showFailedSaveMessage();
    }

    @Test
    public void onSaveClick_throwFileNotFoundException() throws IOException {
        Mockito.when(model.saveBitmap(Mockito.any(), Mockito.any())).thenThrow(new FileNotFoundException());
        detailPresenter.onSaveClick(1, null);
        Mockito.verify(model).saveBitmap(imageInfoList.get(1), null);
        Mockito.verify(detailView).showFailedSaveMessage();
    }

    @Test
    public void onShareClick_isCorrect() throws IOException {
        Mockito.when(model.saveBitmap(Mockito.any(), Mockito.any())).thenReturn("path");
        detailPresenter.onShareClick(1, null);
        Mockito.verify(model).saveBitmap(imageInfoList.get(1), null);
        Mockito.verify(detailView).shareImage("path");
    }

    @Test
    public void onShareClick_throwIOException() throws IOException {
        Mockito.when(model.saveBitmap(Mockito.any(), Mockito.any())).thenThrow(new IOException());
        detailPresenter.onShareClick(1, null);
        Mockito.verify(model).saveBitmap(imageInfoList.get(1), null);
        Mockito.verify(detailView).showFailedSaveMessage();
    }

    @Test
    public void onShareClick_throwFileNotFoundException() throws IOException {
        Mockito.when(model.saveBitmap(Mockito.any(), Mockito.any())).thenThrow(new FileNotFoundException());
        detailPresenter.onShareClick(1, null);
        Mockito.verify(model).saveBitmap(imageInfoList.get(1), null);
        Mockito.verify(detailView).showFailedSaveMessage();
    }
}