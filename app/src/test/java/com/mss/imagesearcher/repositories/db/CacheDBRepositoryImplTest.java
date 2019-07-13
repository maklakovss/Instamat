package com.mss.imagesearcher.repositories.db;

import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.domain.repositories.CacheDBRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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

        assertEquals(images.size(), cacheDBRepository.getImagesInfo("one", 1).blockingGet().size());

        verify(imageInfoDao).getImagesInfo("one", 1);
    }

    @Test
    public void deleteImages_callImageInfoDao() {
        when(cacheDB.productDao()).thenReturn(imageInfoDao);

        cacheDBRepository.deleteImages("one").blockingGet();

        verify(imageInfoDao).deleteQuery("one");
    }
}