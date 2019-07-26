package com.mss.imagesearcher.presenter.imagelist;

import com.mss.imagesearcher.domain.ImageListModel;
import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.view.imagelist.IImageListViewHolder;
import com.mss.imagesearcher.view.imagelist.ImageListView;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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
        imageListPresenter = spy(new ImageListPresenter(model));
        imageListPresenter.attachView(imageListView);
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
    public void onItemClick_openDetailActivity() {
        imageListPresenter.onItemClick(1);
        verify(imageListView).openDetailActivity(1);
    }

    @Test
    public void getRvPresenter_returnNotNull() {
        assertNotNull(imageListPresenter.getRvPresenter());
    }

    @Test
    public void onSearchClick_showResultFromNetwork() {
        initImageInfoList();
        when(model.getImagesFromNetwork(anyString(), eq(1))).thenReturn(Maybe.just(imageInfoList));

        imageListPresenter.onSearchClick("one");

        verify(imageListView).showProgress(true);
        verify(model).getImagesFromNetwork("one", 1);
        verify(imageListView, times(2)).refreshImageList();
        verify(imageListView).showProgress(false);
    }

    @Test
    public void onSearchClick_queryReturnNetworkException_showNotFoundMessage() {
        when(model.getImages()).thenReturn(new ArrayList<>());
        when(model.getImagesFromNetwork(anyString(), eq(1))).thenReturn(Maybe.error(new Exception()));

        imageListPresenter.onSearchClick("one");

        verify(imageListView).showProgress(true);
        verify(model).getImagesFromNetwork("one", 1);
        verify(imageListView, times(1)).refreshImageList();
        verify(imageListView).showProgress(false);
        verify(imageListView).showNotFoundMessage();
    }

    @Test
    public void onNeedNextPage_showResultFromNetwork() {
        initImageInfoList();
        when(model.getImagesFromNetwork(anyString(), eq(1))).thenReturn(Maybe.just(imageInfoList));

        imageListPresenter.onNeedNextPage();

        verify(imageListView).showProgress(true);
        verify(model, times(0)).getImagesFromNetwork("one", 1);
        verify(imageListView).refreshImageList();
        verify(imageListView).showProgress(false);
    }

    @Test
    public void onRefresh_showResultFromNetwork() {
        initImageInfoList();
        when(model.getImagesFromNetwork(anyString(), eq(1))).thenReturn(Maybe.just(imageInfoList));

        imageListPresenter.onRefresh("one");

        verify(imageListView).showProgress(true);
        verify(model).getImagesFromNetwork("one", 1);
        verify(imageListView, times(2)).refreshImageList();
        verify(imageListView).showProgress(false);
    }

    @Test
    public void onCreate_InitAdMob() {
        imageListPresenter.onCreate();

        verify(imageListView).initAdMob();
    }

    @Test
    public void onPrivacyPolicyClick_ShowPrivacyPolicy() {
        imageListPresenter.onPrivacyPolicyClick();

        verify(imageListView).showPrivacyPolicy();
    }

    @Test
    public void RvPresenterBindView_showProgressSetImage() {
        initImageInfoList();
        IImageListViewHolder holder = mock(IImageListViewHolder.class);
        when(holder.getPos()).thenReturn(1);

        imageListPresenter.getRvPresenter().bindView(holder);

        verify(holder).showProgress(true);
        verify(holder).setImage(imageInfoList.get(1).getWebFormatURL());
    }

    @Test
    public void RvPresenterGetItemCount_returnImagesCount() {
        initImageInfoList();

        assertEquals(imageInfoList.size(), imageListPresenter.getRvPresenter().getItemCount());
    }

    @Test
    public void RvPresenterOnImageLoaded_stopProgress() {
        IImageListViewHolder holder = mock(IImageListViewHolder.class);

        imageListPresenter.getRvPresenter().onImageLoaded(holder);

        verify(holder).showProgress(false);
    }

    @Test
    public void RvPresenterOnImageLoadFailed_stopProgress() {
        IImageListViewHolder holder = mock(IImageListViewHolder.class);

        imageListPresenter.getRvPresenter().onImageLoadFailed(holder);

        verify(holder).showProgress(false);
    }
}