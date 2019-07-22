package com.mss.imagesearcher.repositories.network;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.google.gson.GsonBuilder;
import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.domain.repositories.ImagesNetRepository;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ImagesNetRepositoryImplAndroidTest {

    private ImagesNetRepository repository;
    private static MockWebServer mockWebServer;

    @BeforeClass
    public static void setupServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @Before
    public void setUp() {
        repository = new ImagesNetRepositoryImpl(new Retrofit.Builder()
                .baseUrl("https://pixabay.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor()).build())
                .build()
                .create(PixabayAPI.class));
    }

    @Test
    public void findImages() {
        List<ImageInfo> images = repository.findImages("one", 1).blockingGet();

        assertEquals(50, images.size());
    }

    @AfterClass
    public static void shutdownServer() throws IOException {
        mockWebServer.shutdown();
    }
}