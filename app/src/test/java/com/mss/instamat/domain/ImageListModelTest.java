package com.mss.instamat.domain;

import com.mss.instamat.domain.models.ImageInfo;
import com.mss.instamat.domain.repositories.CacheDBRepository;
import com.mss.instamat.domain.repositories.FilesRepository;
import com.mss.instamat.domain.repositories.ImagesNetRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ImageListModelTest {

    private ImageListModel model;
    private List<ImageInfo> images;

    @Mock
    private CacheDBRepository cacheDBRepository;
    @Mock
    private ImagesNetRepository imagesNetRepository;
    @Mock
    private FilesRepository filesRepository;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        model = new ImageListModel(cacheDBRepository, imagesNetRepository, filesRepository);
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
    public void getImagesFromNetwork_callNetRepositoryAndSaveResult() {
        initImageInfoList();
        when(imagesNetRepository.findImages(anyString(), anyInt())).thenReturn(Maybe.just(images));

        assertEquals(model.getImagesFromNetwork("one", 1).blockingGet().size(), images.size());

        verify(imagesNetRepository).findImages("one", 1);
        assertEquals(model.getImages().size(), images.size());

        model.clearImages();
        assertEquals(model.getImages().size(), 0);
    }

    @Test
    public void getImagesFromCacheDB_callBDRepositoryAndSaveResult() {
        initImageInfoList();
        when(cacheDBRepository.getImagesInfo(anyString(), anyInt())).thenReturn(Single.just(images));

        assertEquals(model.getImagesFromCacheDB("one", 1).blockingGet().size(), images.size());

        verify(cacheDBRepository).getImagesInfo("one", 1);
        assertEquals(model.getImages().size(), images.size());
    }

    @Test
    public void saveToCacheDBAsync() {
        initImageInfoList();
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        when(cacheDBRepository.insertToCacheDB(anyString(), anyInt(), anyList())).thenReturn(Single.just(ids));

        assertEquals(model.saveToCacheDBAsync("one", 1, images).blockingGet().size(), 1);

        verify(cacheDBRepository).insertToCacheDB("one", 1, images);
    }

    @Test
    public void saveBitmap() throws IOException {
        initImageInfoList();
        when(filesRepository.saveBitmap(any(), any())).thenReturn("path");

        assertEquals(model.saveBitmap(images.get(0), null), "path");

        verify(filesRepository).saveBitmap(images.get(0), null);
    }
}