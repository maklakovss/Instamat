package com.mss.instamat.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.GsonBuilder;
import com.mss.instamat.BuildConfig;
import com.mss.instamat.model.CacheDBRepository;
import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.presenter.imagelist.ImageListPresenter;
import com.mss.instamat.repositories.db.CacheDB;
import com.mss.instamat.repositories.db.CacheDBRepositoryImpl;
import com.mss.instamat.repositories.network.ImagesRepository;
import com.mss.instamat.repositories.network.PixabayAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private static final String URL = "https://pixabay.com/";
    private static final String DATABASE_NAME = "USERS";

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
    CacheDB provideCacheDB() {
        return Room.databaseBuilder(appContext, CacheDB.class, DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    CacheDBRepository provideCacheDBRepository(CacheDB cacheDB) {
        return new CacheDBRepositoryImpl(cacheDB);
    }

    @Singleton
    @Provides
    PixabayAPI providePixabayAPI() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)).build())
                .build()
                .create(PixabayAPI.class);
    }

    @Singleton
    @Provides
    ImagesRepository provideImagesRepository(PixabayAPI pixabayAPI) {
        return new ImagesRepository(pixabayAPI);
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
