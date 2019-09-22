package com.mss.imagesearcher.repositories.db;

import android.content.Context;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.domain.repositories.CacheDBRepository;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4ClassRunner.class)
public class CacheDBRepositoryImplAndroidTest {

    private CacheDBRepository repository;
    private List<ImageInfo> imageInfoList;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getContext();
        repository = new CacheDBRepositoryImpl(Room.inMemoryDatabaseBuilder(context, CacheDB.class).build());
    }

}