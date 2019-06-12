package com.mss.instamat.tests.mockito.model;


import android.support.annotation.NonNull;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    @NonNull
    @GET("/users/{user}")
    Single<User> getUser(@NonNull @Path("user") String userName);
}
