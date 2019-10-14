package com.mss.imagesearcher.model

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.mss.imagesearcher.model.entity.ImageInfo
import com.mss.imagesearcher.model.repositories.FilesRepository
import com.mss.imagesearcher.model.repositories.ImagesNetRepository
import io.reactivex.Maybe
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject

class ImageListModel @Inject
constructor(val imagesNetRepository: ImagesNetRepository,
            val filesRepository: FilesRepository) {

    private val imageInfoList: MutableList<ImageInfo> = ArrayList()

    val images: List<ImageInfo> get() = imageInfoList
    val currentImage = MutableLiveData<ImageInfo>(null)
    val currentSearchString = MutableLiveData<String>("")
    val needShowPage = MutableLiveData<PageType>(PageType.NONE)

    enum class PageType {
        NONE, HISTORY, SETTINGS, LIST, DETAIL, INFO
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
        currentImage.value = null
        Timber.d("Image list cleared")
    }

    @Throws(IOException::class)
    fun saveBitmap(imageInfo: ImageInfo, bitmap: Bitmap): String {
        val path = filesRepository.saveBitmap(imageInfo, bitmap)
        Timber.d("Image saved to path - %s", path)
        return path
    }

    fun setCurrentQuery(searchText: String) {
        currentSearchString.value = searchText
    }
}
