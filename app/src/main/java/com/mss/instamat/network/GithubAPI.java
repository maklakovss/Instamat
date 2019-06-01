package com.mss.instamat.network;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubAPI {

    @NonNull
    @GET("users/{user}")
    Observable<AvatarData> loadAvatarData(@NonNull @Path("user") final String user);
}
