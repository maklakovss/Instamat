package com.mss.imagesearcher.di

import com.mss.imagesearcher.domain.repositories.FilesRepository
import com.mss.imagesearcher.repositories.files.FilesHelper
import com.mss.imagesearcher.repositories.files.FilesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FilesModule {

    @Provides
    @Singleton
    fun provideFilesHelper(): FilesHelper {
        return FilesHelper()
    }

    @Provides
    @Singleton
    fun provideFilesRepository(filesHelper: FilesHelper): FilesRepository {
        return FilesRepositoryImpl(filesHelper)
    }
}
