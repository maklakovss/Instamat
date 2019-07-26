package com.mss.imagesearcher.di;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.presenter.detail.DetailPresenter;
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter;
import com.mss.imagesearcher.presenter.info.InfoPresenter;
import com.mss.imagesearcher.view.helpers.ImageLoader;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class MockModule {

    @Singleton
    @Provides
    @NonNull
    ImageLoader provideImageLoader() {
        return Mockito.mock(ImageLoader.class);
    }

    @Singleton
    @Provides
    @NonNull
    ImageListPresenter provideImageListPresenter() {
        return Mockito.mock(ImageListPresenter.class);
    }

    @Singleton
    @Provides
    @NonNull
    DetailPresenter provideDetailPresenter() {
        return Mockito.mock(DetailPresenter.class);
    }

    @Singleton
    @Provides
    @NonNull
    InfoPresenter provideInfoPresenter() {
        return Mockito.mock(InfoPresenter.class);
    }
}
