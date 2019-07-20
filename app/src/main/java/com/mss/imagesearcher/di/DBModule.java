package com.mss.imagesearcher.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.mss.imagesearcher.domain.repositories.CacheDBRepository;
import com.mss.imagesearcher.repositories.db.CacheDB;
import com.mss.imagesearcher.repositories.db.CacheDBRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    private static final String DATABASE_NAME = "CACHE";

    @Singleton
    @Provides
    @NonNull
    CacheDB provideCacheDB(@NonNull final Context context) {
        return Room.databaseBuilder(context, CacheDB.class, DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    @NonNull
    CacheDBRepository provideCacheDBRepository(@NonNull final CacheDB cacheDB) {
        return new CacheDBRepositoryImpl(cacheDB);
    }
}