package com.mss.imagesearcher.di

import android.content.Context
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.model.repositories.DBRepository
import com.mss.imagesearcher.model.repositories.FilesRepository
import com.mss.imagesearcher.model.repositories.ImagesNetRepository
import com.mss.imagesearcher.presenter.history.HistoryPresenter
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter
import com.mss.imagesearcher.presenter.info.InfoPresenter
import com.mss.imagesearcher.presenter.main.MainPresenter
import com.mss.imagesearcher.presenter.settings.SettingsPresenter
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
            filesRepository: FilesRepository,
            dbRepository: DBRepository): ImageListModel {
        return ImageListModel(//cacheDBRepository,
                imagesNetRepository,
                filesRepository,
                dbRepository)
    }

    @Singleton
    @Provides
    fun provideMainPresenter(model: ImageListModel): MainPresenter {
        return MainPresenter(model)
    }

    @Singleton
    @Provides
    fun provideHistoryPresenter(model: ImageListModel): HistoryPresenter {
        return HistoryPresenter(model)
    }

    @Singleton
    @Provides
    fun provideSettingsPresenter(model: ImageListModel): SettingsPresenter {
        return SettingsPresenter(model)
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
