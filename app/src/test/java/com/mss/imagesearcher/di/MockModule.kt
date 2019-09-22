package com.mss.imagesearcher.di

import com.mss.imagesearcher.presenter.detail.DetailPresenter
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter
import com.mss.imagesearcher.presenter.info.InfoPresenter
import com.mss.imagesearcher.view.helpers.ImageLoader
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

@Module
internal class MockModule {

    @Singleton
    @Provides
    fun provideImageLoader(): ImageLoader {
        return Mockito.mock(ImageLoader::class.java)
    }

    @Singleton
    @Provides
    fun provideImageListPresenter(): ImageListPresenter {
        return Mockito.mock(ImageListPresenter::class.java)
    }

    @Singleton
    @Provides
    fun provideDetailPresenter(): DetailPresenter {
        return Mockito.mock(DetailPresenter::class.java)
    }

    @Singleton
    @Provides
    fun provideInfoPresenter(): InfoPresenter {
        return Mockito.mock(InfoPresenter::class.java)
    }
}
