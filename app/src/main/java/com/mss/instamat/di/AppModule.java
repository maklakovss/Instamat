package com.mss.instamat.di;

import android.content.Context;

import com.mss.instamat.model.CacheDBRepository;
import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.presenter.imagelist.ImageListPresenter;
import com.mss.instamat.repositories.network.ImagesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

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
    ImageListModel provideImageListModel(CacheDBRepository cacheDBRepository, ImagesRepository imagesRepository) {
        return new ImageListModel(cacheDBRepository, imagesRepository);
    }

    @Singleton
    @Provides
    ImageListPresenter provideImageListPresenter(ImageListModel model) {
        return new ImageListPresenter(model);
    }
}
