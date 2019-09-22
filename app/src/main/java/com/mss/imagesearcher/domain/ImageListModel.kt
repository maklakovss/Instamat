package com.mss.imagesearcher.domain

import android.graphics.Bitmap
import com.mss.imagesearcher.domain.models.ImageInfo
import com.mss.imagesearcher.domain.repositories.FilesRepository
import com.mss.imagesearcher.domain.repositories.ImagesNetRepository
import io.reactivex.Maybe
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject

class ImageListModel @Inject
constructor(val imagesNetRepository: ImagesNetRepository,
            val filesRepository: FilesRepository) {

    private val imageInfoList: MutableList<ImageInfo>

    val images: List<ImageInfo>
        get() = imageInfoList

    init {
        imageInfoList = ArrayList()
    }

    fun getImagesFromNetwork(searchText: String, page: Int): Maybe<List<ImageInfo>> {
        return imagesNetRepository.findImages(searchText, page)
                .doOnSuccess { images ->
                    imageInfoList.addAll(images)
                    Timber.d("Added from network %d images on query '%s' page %d, all - %d",
                            images.size,
                            searchText,
                            page,
                            imageInfoList.size)
                }
                .doOnError { Timber.e(it) }
    }

    fun clearImages() {
        imageInfoList.clear()
        Timber.d("Image list cleared")
    }

    @Throws(IOException::class)
    fun saveBitmap(imageInfo: ImageInfo, bitmap: Bitmap): String {
        val path = filesRepository.saveBitmap(imageInfo, bitmap)
        Timber.d("Image saved to path - %s", path)
        return path
    }
}
