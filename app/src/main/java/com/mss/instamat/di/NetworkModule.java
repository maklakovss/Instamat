package com.mss.instamat.di;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.mss.instamat.domain.repositories.ImagesNetRepository;
import com.mss.instamat.repositories.network.ImagesNetRepositoryImpl;
import com.mss.instamat.repositories.network.PixabayAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String URL = "https://pixabay.com/";

    @Singleton
    @Provides
    @NonNull
    PixabayAPI providePixabayAPI() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .client(new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build())
//                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)).build())
                .build()
                .create(PixabayAPI.class);
    }

    @Singleton
    @Provides
    @NonNull
    ImagesNetRepository provideImagesRepository(@NonNull final PixabayAPI pixabayAPI) {
        return new ImagesNetRepositoryImpl(pixabayAPI);
    }
}