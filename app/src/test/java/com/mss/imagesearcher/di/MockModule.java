package com.mss.imagesearcher.di;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.presenter.detail.DetailPresenter;
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter;
import com.mss.imagesearcher.view.helpers.ImageLoader;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockModule {

//    @Singleton
//    @Provides
//    @NonNull
//    CacheDB provideCacheDB() {
//        return Mockito.mock(CacheDB.class);
//    }
//
//    @Singleton
//    @Provides
//    @NonNull
//    CacheDBRepository provideCacheDBRepository(@NonNull final CacheDB cacheDB) {
//        return Mockito.mock(CacheDBRepository.class);
//    }
//
//    @Singleton
//    @Provides
//    @NonNull
//    PixabayAPI providePixabayAPI() {
//        return Mockito.mock(PixabayAPI.class);
//    }
//
//    @Singleton
//    @Provides
//    @NonNull
//    ImagesNetRepository provideImagesRepository() {
//        return Mockito.mock(ImagesNetRepository.class);
//    }
//
//    @Provides
//    @Singleton
//    @NonNull
//    FilesHelper provideFilesHelper() {
//        return new FilesHelper();
//    }
//
//    @Provides
//    @Singleton
//    @NonNull
//    FilesRepository provideFilesRepository(FilesHelper filesHelper) {
//        return new FilesRepositoryImpl(filesHelper);
//    }
//
//    @Singleton
//    @Provides
//    @NonNull
//    ImageListModel provideImageListModel(@NonNull final CacheDBRepository cacheDBRepository,
//                                         @NonNull final ImagesNetRepository imagesNetRepository,
//                                         @NonNull final FilesRepository filesRepository) {
//        return Mockito.mock(ImageListModel.class);
//    }

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
}
