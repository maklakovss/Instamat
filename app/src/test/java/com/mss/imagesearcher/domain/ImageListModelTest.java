package com.mss.imagesearcher.domain;

import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.domain.repositories.CacheDBRepository;
import com.mss.imagesearcher.domain.repositories.FilesRepository;
import com.mss.imagesearcher.domain.repositories.ImagesNetRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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
    public void setUp() {
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

        assertEquals(images.size(), model.getImagesFromNetwork("one", 1).blockingGet().size());

        verify(imagesNetRepository).findImages("one", 1);
        assertEquals(images.size(), model.getImages().size());

        model.clearImages();
        assertEquals(0, model.getImages().size());
    }

    @Test
    public void getImagesFromCacheDB_callDBRepositoryAndSaveResult() {
        initImageInfoList();
        when(cacheDBRepository.getImagesInfo(anyString(), anyInt())).thenReturn(Single.just(images));

        assertEquals(images.size(), model.getImagesFromCacheDB("one", 1).blockingGet().size());

        verify(cacheDBRepository).getImagesInfo("one", 1);
        assertEquals(images.size(), model.getImages().size());
    }

    @Test
    public void saveToCacheDBAsync_callDBRepository() {
        initImageInfoList();
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        when(cacheDBRepository.insertToCacheDB(anyString(), anyInt(), anyList())).thenReturn(Single.just(ids));

        assertEquals(1, model.saveToCacheDBAsync("one", 1, images).blockingGet().size());

        verify(cacheDBRepository).insertToCacheDB("one", 1, images);
    }

    @Test
    public void deleteImagesFromCache_callDBRepository() {
        when(cacheDBRepository.deleteImages(anyString())).thenReturn(Completable.complete());

        model.deleteImagesFromCache("one").blockingGet();

        verify(cacheDBRepository).deleteImages("one");
    }

    @Test
    public void saveBitmap() throws IOException {
        initImageInfoList();
        when(filesRepository.saveBitmap(any(), any())).thenReturn("path");

        assertEquals("path", model.saveBitmap(images.get(0), null));

        verify(filesRepository).saveBitmap(images.get(0), null);
    }
}