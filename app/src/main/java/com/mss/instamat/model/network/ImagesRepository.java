package com.mss.instamat.model.network;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.mss.instamat.BuildConfig;
import com.mss.instamat.model.ImagesResponse;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImagesRepository {

    private static final String KEY = "12657704-170e4200b7841bc79a107ea6e";
    private static final String LANG = "ru";
    private static final String IMAGE_TYPE = "photo";
    private static final String URL = "https://pixabay.com/";
    private static final int IMAGES_PER_PAGE = 50;

    private final PixabayAPI pixabayAPI;

    public ImagesRepository() {
        pixabayAPI = new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)).build())
                .build()
                .create(PixabayAPI.class);
    }

    @NonNull
    public Maybe<ImagesResponse> findImages(@NonNull final String query, int pageNumber) {
        return pixabayAPI
                .findImages(KEY, query, LANG, IMAGE_TYPE, pageNumber, IMAGES_PER_PAGE)
                .subscribeOn(Schedulers.io());
    }
}
