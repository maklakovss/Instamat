package com.mss.instamat.repositories.network;

import android.support.annotation.NonNull;

import com.mss.instamat.repositories.network.models.ImagesResponse;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayAPI {

    @NonNull
    @GET("api")
    Maybe<ImagesResponse> findImages(@NonNull final @Query("key") String key,
                                     @NonNull final @Query("q") String query,
                                     @NonNull final @Query("lang") String lang,
                                     @NonNull final @Query("image_type") String imageType,
                                     @Query("page") int pageNumber,
                                     @Query("per_page") int imagesPerPage);
}
