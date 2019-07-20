package com.mss.imagesearcher.di;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.domain.repositories.FilesRepository;
import com.mss.imagesearcher.repositories.files.FilesHelper;
import com.mss.imagesearcher.repositories.files.FilesRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FilesModule {

    @Provides
    @Singleton
    @NonNull
    FilesHelper provideFilesHelper() {
        return new FilesHelper();
    }

    @Provides
    @Singleton
    @NonNull
    FilesRepository provideFilesRepository(FilesHelper filesHelper) {
        return new FilesRepositoryImpl(filesHelper);
    }
}
