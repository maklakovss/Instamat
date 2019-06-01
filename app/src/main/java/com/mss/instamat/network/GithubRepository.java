package com.mss.instamat.network;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubRepository {

    private final GithubAPI githubAPI;

    public GithubRepository() {
        githubAPI = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubAPI.class);
    }

    @NonNull
    Observable<AvatarData> loadAvatarData(@NonNull final String login) {
        return githubAPI.loadAvatarData(login);
    }
}
