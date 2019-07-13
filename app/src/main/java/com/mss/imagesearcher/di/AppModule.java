package com.mss.imagesearcher.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mss.imagesearcher.domain.ImageListModel;
import com.mss.imagesearcher.domain.repositories.CacheDBRepository;
import com.mss.imagesearcher.domain.repositories.FilesRepository;
import com.mss.imagesearcher.domain.repositories.ImagesNetRepository;
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter;
import com.mss.imagesearcher.view.helpers.ImageLoader;
import com.mss.imagesearcher.view.helpers.ImageLoaderImpl;

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
                                         @NonNull final ImagesNetRepository imagesNetRepository,
                                         @NonNull final FilesRepository filesRepository) {
        return new ImageListModel(cacheDBRepository, imagesNetRepository, filesRepository);
    }

    @Singleton
    @Provides
    @NonNull
    ImageListPresenter provideImageListPresenter(@NonNull final ImageListModel model) {
        return new ImageListPresenter(model);
    }

    @Singleton
    @Provides
    @NonNull
    ImageLoader provideImageLoader() {
        return new ImageLoaderImpl();
    }
}
