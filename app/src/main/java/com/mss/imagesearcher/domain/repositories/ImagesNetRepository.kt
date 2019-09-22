package com.mss.imagesearcher.domain.repositories

import com.mss.imagesearcher.domain.models.ImageInfo

import io.reactivex.Maybe

interface ImagesNetRepository {

    fun findImages(query: String, pageNumber: Int): Maybe<List<ImageInfo>>
}
