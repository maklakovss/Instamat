package com.mss.imagesearcher.model.repositories

import com.mss.imagesearcher.model.entity.ImageInfo
import com.mss.imagesearcher.model.entity.QueryParams

import io.reactivex.Maybe

interface ImagesNetRepository {

    fun findImages(query: QueryParams, pageNumber: Int): Maybe<List<ImageInfo>>
}
