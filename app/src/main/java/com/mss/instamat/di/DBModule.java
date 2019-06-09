package com.mss.instamat.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mss.instamat.model.CacheDBRepository;
import com.mss.instamat.repositories.db.CacheDB;
import com.mss.instamat.repositories.db.CacheDBRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    private static final String DATABASE_NAME = "CACHE";

    @Singleton
    @Provides
    CacheDB provideCacheDB(Context context) {
        return Room.databaseBuilder(context, CacheDB.class, DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    CacheDBRepository provideCacheDBRepository(CacheDB cacheDB) {
        return new CacheDBRepositoryImpl(cacheDB);
    }
}