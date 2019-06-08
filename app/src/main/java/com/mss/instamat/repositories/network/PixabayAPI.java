package com.mss.instamat.repositories.network;

import com.mss.instamat.model.models.ImagesResponse;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayAPI {

    @GET("api")
    Maybe<ImagesResponse> findImages(@Query("key") String key,
                                     @Query("q") String query,
                                     @Query("lang") String lang,
                                     @Query("image_type") String imageType,
                                     @Query("page") int pageNumber,
                                     @Query("per_page") int imagesPerPage);
}
