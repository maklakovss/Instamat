package com.mss.imagesearcher.model.repositories

import com.mss.imagesearcher.model.entity.ImageInfo

import io.reactivex.Maybe

interface ImagesNetRepository {

    fun findImages(query: String, pageNumber: Int): Maybe<List<ImageInfo>>
}
