package com.mss.instamat.tests.mockito.model;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubApi {

    private final GithubService githubService;

    public GithubApi() {

        final Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        final GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);

        final RxJava2CallAdapterFactory factory = RxJava2CallAdapterFactory.create();

        githubService = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(factory)
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(GithubService.class);
    }

    @NonNull
    public Single<User> getUser(@NonNull String login) {
        return githubService.getUser(login)
                .subscribeOn(Schedulers.io());
    }
}
