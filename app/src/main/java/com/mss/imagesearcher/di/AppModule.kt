package com.mss.imagesearcher.di

import android.content.Context
import com.mss.imagesearcher.domain.ImageListModel
import com.mss.imagesearcher.domain.repositories.FilesRepository
import com.mss.imagesearcher.domain.repositories.ImagesNetRepository
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter
import com.mss.imagesearcher.presenter.info.InfoPresenter
import com.mss.imagesearcher.view.helpers.ImageLoader
import com.mss.imagesearcher.view.helpers.ImageLoaderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideImageListModel(//@NonNull final CacheDBRepository cacheDBRepository,
            imagesNetRepository: ImagesNetRepository,
            filesRepository: FilesRepository): ImageListModel {
        return ImageListModel(//cacheDBRepository,
                imagesNetRepository,
                filesRepository)
    }

    @Singleton
    @Provides
    fun provideImageListPresenter(model: ImageListModel): ImageListPresenter {
        return ImageListPresenter(model)
    }

    @Singleton
    @Provides
    fun provideInfoPresenter(model: ImageListModel): InfoPresenter {
        return InfoPresenter(model)
    }

    @Singleton
    @Provides
    fun provideImageLoader(): ImageLoader {
        return ImageLoaderImpl()
    }
}
