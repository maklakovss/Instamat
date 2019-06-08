package com.mss.instamat.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mss.instamat.model.CacheDBRepository;
import com.mss.instamat.presenter.imagelist.ImageListPresenter;
import com.mss.instamat.repositories.db.CacheDB;
import com.mss.instamat.repositories.db.CacheDBRepositoryImpl;
import com.mss.instamat.repositories.network.ImagesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private static final String DATABASE_NAME = "USERS";

    private final Context appContext;

    public AppModule(Context appContext) {
        this.appContext = appContext;
    }


    @Singleton
    @Provides
    Context provideAppContext() {
        return appContext;
    }

    @Singleton
    @Provides
    CacheDBRepository provideCacheDBRepository() {
        return new CacheDBRepositoryImpl(Room.databaseBuilder(appContext, CacheDB.class, DATABASE_NAME).build());

    }

    @Singleton
    @Provides
    ImagesRepository provideImagesRepository() {
        return new ImagesRepository();

    }

    @Singleton
    @Provides
    ImageListPresenter provideImageListPresenter() {
        return new ImageListPresenter();
    }
}
