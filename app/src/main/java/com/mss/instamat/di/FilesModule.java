package com.mss.instamat.di;

import android.support.annotation.NonNull;

import com.mss.instamat.domain.repositories.FilesRepository;
import com.mss.instamat.repositories.files.FilesRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FilesModule {

    @Provides
    @Singleton
    @NonNull
    FilesRepository provideFilesRepository() {
        return new FilesRepositoryImpl();
    }
}
