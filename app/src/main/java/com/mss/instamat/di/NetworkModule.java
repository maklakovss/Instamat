package com.mss.instamat.di;

import com.google.gson.GsonBuilder;
import com.mss.instamat.BuildConfig;
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
public class NetworkModule {

    private static final String URL = "https://pixabay.com/";

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
}