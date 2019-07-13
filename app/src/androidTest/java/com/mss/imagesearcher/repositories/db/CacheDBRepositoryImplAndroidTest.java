package com.mss.imagesearcher.repositories.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.domain.repositories.CacheDBRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CacheDBRepositoryImplAndroidTest {

    private CacheDBRepository repository;
    private List<ImageInfo> imageInfoList;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getContext();
        repository = new CacheDBRepositoryImpl(Room.inMemoryDatabaseBuilder(context, CacheDB.class).build());
    }

    @Test
    public void insertAndSelect_returnInsertedRows() {
        initImageInfoList();

        List<Long> list = repository.insertToCacheDB("query", 1, imageInfoList).blockingGet();

        assertEquals(50, list.size());

        List<ImageInfo> images = repository.getImagesInfo("query",2).blockingGet();

        assertEquals(0, images.size());

        images = repository.getImagesInfo("query1",1).blockingGet();

        assertEquals(0, images.size());

        images = repository.getImagesInfo("query",1).blockingGet();

        assertEquals(50, images.size());
    }

    private void initImageInfoList() {
        imageInfoList = new ArrayList();
        for (int i = 0; i < 50; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setId(i);
            imageInfo.setPreviewURL("https:\\\\previewurl" + i);
            imageInfo.setLargeImageURL("https:\\\\largeimagewurl" + i);
            imageInfoList.add(imageInfo);
        }
    }

}