package com.mss.instamat.repositories.db;

import com.mss.instamat.domain.models.ImageInfo;
import com.mss.instamat.domain.repositories.CacheDBRepository;
import com.mss.instamat.presenter.detail.DetailPresenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CacheDBRepositoryImplTest {


    private CacheDBRepository cacheDBRepository;
    private List<ImageInfo> images;

    @Mock
    private CacheDB cacheDB;

    @Mock
    private ImageInfoDao imageInfoDao;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        cacheDBRepository = spy(new CacheDBRepositoryImpl(cacheDB));
    }

    private void initImageInfoList() {
        images = new ArrayList();
        for (int i = 0; i < 50; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setId(i);
            imageInfo.setPreviewURL("https:\\\\previewurl" + i);
            imageInfo.setLargeImageURL("https:\\\\largeimagewurl" + i);
            images.add(imageInfo);
        }
    }

    @Test
    public void insertToCacheDB_callImageInfoDao() {
        initImageInfoList();
        when(cacheDB.productDao()).thenReturn(imageInfoDao);
        when(imageInfoDao.insertAll(anyList())).thenReturn(new ArrayList<>());

        cacheDBRepository.insertToCacheDB("one", 1, images).blockingGet();

        verify(imageInfoDao).insertAll(anyList());
    }

    @Test
    public void getImagesInfo_callImageInfoDao() {
        initImageInfoList();
        when(cacheDB.productDao()).thenReturn(imageInfoDao);
        when(imageInfoDao.getImagesInfo("one", 1))
                .thenReturn(Single.just(DBMapper.mapToDB("one", 1, images)));

        assertEquals(cacheDBRepository.getImagesInfo("one", 1).blockingGet().size(), images.size());

        verify(imageInfoDao).getImagesInfo("one", 1);
    }
}