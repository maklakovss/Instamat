package com.mss.imagesearcher.model.repositories.network

import com.mss.imagesearcher.model.repositories.network.entity.ImagesResponse

import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("api/")
    fun findImages(@Query("key") key: String,
                   @Query("q") query: String?,
                   @Query("lang") lang: String,
                   @Query("image_type") imageType: String?,
                   @Query("orientation") orientation: String?,
                   @Query("category") category: String?,
                   @Query("min_width") minWidth: Int?,
                   @Query("min_height") minHeight: Int?,
                   @Query("colors") colors: String?,
                   @Query("order") order: String?,
                   @Query("page") pageNumber: Int,
                   @Query("per_page") imagesPerPage: Int): Maybe<ImagesResponse>
}
