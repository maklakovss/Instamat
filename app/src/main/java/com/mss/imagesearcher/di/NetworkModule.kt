package com.mss.imagesearcher.di

import com.google.gson.GsonBuilder
import com.mss.imagesearcher.domain.repositories.ImagesNetRepository
import com.mss.imagesearcher.repositories.network.ImagesNetRepositoryImpl
import com.mss.imagesearcher.repositories.network.PixabayAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private val URL = "https://pixabay.com/"
    }

    @Singleton
    @Provides
    fun providePixabayAPI(): PixabayAPI {
        return Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                //                .client(new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build())
                //                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)).build())
                .build()
                .create(PixabayAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideImagesRepository(pixabayAPI: PixabayAPI): ImagesNetRepository {
        return ImagesNetRepositoryImpl(pixabayAPI)
    }
}