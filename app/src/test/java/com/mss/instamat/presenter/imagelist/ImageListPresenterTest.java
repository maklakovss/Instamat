package com.mss.instamat.presenter.imagelist;

import com.mss.instamat.domain.ImageListModel;
import com.mss.instamat.domain.models.ImageInfo;
import com.mss.instamat.view.imagelist.ImageListView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

public class ImageListPresenterTest {

    @Mock
    private ImageListView imageListView;

    @Mock
    private ImageListModel model;

    private ImageListPresenter imageListPresenter;
    private List<ImageInfo> imageInfoList;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        imageListPresenter = Mockito.spy(new ImageListPresenter(model));

        imageInfoList = new ArrayList();
        for (int i = 0; i < 50; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setId(i);
            imageInfo.setPreviewURL("https:\\\\previewurl" + i);
            imageInfo.setLargeImageURL("https:\\\\largeimagewurl" + i);
            imageInfoList.add(imageInfo);
        }

        Mockito.when(model.getImages()).thenReturn(imageInfoList);
        imageListPresenter.attachView(imageListView);
    }

    @Test
    public void onItemClick_isCorrect() {
        imageListPresenter.onItemClick(1);
        Mockito.verify(imageListView).openDetailActivity(1);
    }

    @Test
    public void getRvPresenter_isCorrect() {
        Assert.assertNotNull(imageListPresenter.getRvPresenter());
    }

    @Test
    public void onSearchClick_dbResult_isCorrect() {
        Mockito.when(model.getImagesFromCacheDB(Mockito.anyString(), eq(1))).thenReturn(Single.just(imageInfoList));

        imageListPresenter.onSearchClick("one");

        Mockito.verify(imageListView).showProgress(true);
        Mockito.verify(model, times(0)).getImagesFromNetwork("one", 1);
        Mockito.verify(imageListView, times(2)).refreshImageList();
        Mockito.verify(imageListView).showProgress(false);
    }

    @Test
    public void onSearchClick_netResult_isCorrect() {
        Mockito.when(model.getImagesFromCacheDB(Mockito.anyString(), eq(1))).thenReturn(Single.just(new ArrayList()));
        Mockito.when(model.getImagesFromNetwork(Mockito.anyString(), eq(1))).thenReturn(Maybe.just(imageInfoList));
        Mockito.when(model.saveToCacheDBAsync(Mockito.anyString(), eq(1), Mockito.anyList())).thenReturn(Single.just(new ArrayList<>()));

        imageListPresenter.onSearchClick("one");

        Mockito.verify(imageListView).showProgress(true);
        Mockito.verify(model).getImagesFromCacheDB("one", 1);
        Mockito.verify(model).getImagesFromNetwork("one", 1);
        Mockito.verify(model).saveToCacheDBAsync("one", 1, imageInfoList);
        Mockito.verify(imageListView, times(2)).refreshImageList();
        Mockito.verify(imageListView).showProgress(false);
    }

    @Test
    public void onNeedNextPage_dbResult_isCorrect() {
        Mockito.when(model.getImagesFromCacheDB(Mockito.anyString(), eq(1))).thenReturn(Single.just(imageInfoList));
        imageListPresenter.onNeedNextPage();
        Mockito.verify(imageListView).showProgress(true);
        Mockito.verify(imageListView).refreshImageList();
        Mockito.verify(imageListView).showProgress(false);
    }
}