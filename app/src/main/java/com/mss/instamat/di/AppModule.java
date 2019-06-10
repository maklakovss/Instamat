package com.mss.instamat.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mss.instamat.domain.ImageListModel;
import com.mss.instamat.domain.repositories.CacheDBRepository;
import com.mss.instamat.domain.repositories.ImagesNetRepository;
import com.mss.instamat.presenter.imagelist.ImageListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context appContext;

    public AppModule(@NonNull final Context appContext) {
        this.appContext = appContext;
    }

    @Singleton
    @Provides
    @NonNull
    Context provideAppContext() {
        return appContext;
    }

    @Singleton
    @Provides
    @NonNull
    ImageListModel provideImageListModel(@NonNull final CacheDBRepository cacheDBRepository,
                                         @NonNull final ImagesNetRepository imagesNetRepository) {
        return new ImageListModel(cacheDBRepository, imagesNetRepository);
    }

    @Singleton
    @Provides
    @NonNull
    ImageListPresenter provideImageListPresenter(@NonNull final ImageListModel model) {
        return new ImageListPresenter(model);
    }
}
