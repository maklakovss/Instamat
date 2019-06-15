package com.mss.instamat.di;

import android.support.annotation.NonNull;

import com.mss.instamat.domain.repositories.FilesRepository;
import com.mss.instamat.repositories.files.FilesHelper;
import com.mss.instamat.repositories.files.FilesRepositoryImpl;

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
