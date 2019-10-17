package com.mss.imagesearcher.model

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.mss.imagesearcher.model.entity.ImageInfo
import com.mss.imagesearcher.model.entity.QueryParams
import com.mss.imagesearcher.model.repositories.DBRepository
import com.mss.imagesearcher.model.repositories.FilesRepository
import com.mss.imagesearcher.model.repositories.ImagesNetRepository
import io.reactivex.Maybe
import io.reactivex.Single
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject

class ImageListModel @Inject
constructor(val imagesNetRepository: ImagesNetRepository,
            val filesRepository: FilesRepository,
            val dbRepository: DBRepository) {

    private val imageInfoList: MutableList<ImageInfo> = ArrayList()
    val images: List<ImageInfo> get() = imageInfoList

    val currentImage = MutableLiveData<ImageInfo>(null)
    val currentQuery = MutableLiveData<QueryParams>()
    val needShowPage = MutableLiveData<PageType>(PageType.NONE)
    val queryParamsList = MutableLiveData<MutableList<QueryParams>>(arrayListOf())

    enum class PageType {
        NONE, HISTORY, SETTINGS, LIST, DETAIL, INFO
    }

    init {
        currentQuery.observeForever { queryParams ->
            if (queryParams != null) {
                dbRepository.addQueryInHistory(queryParams)
                        .subscribe(
                                {
                                    queryParamsList.value?.add(0, queryParams)
                                    queryParamsList.postValue(queryParamsList.value)
                                },
                                { Timber.e(it) }
                        )
            }
        }
    }

    fun getImagesFromNetwork(query: QueryParams, page: Int): Maybe<List<ImageInfo>> {
        return imagesNetRepository.findImages(query, page)
                .doOnSuccess { images ->
                    imageInfoList.addAll(images)
                    Timber.d("Added from network %d images on query '%s' page %d, all - %d",
                            images.size,
                            query.toString(),
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
        if (currentQuery.value == null) {
            currentQuery.value = QueryParams(query = searchText)
        } else {
            currentQuery.value?.query = searchText
        }
    }

    fun clearHistory(): Single<Int> {
        queryParamsList.value?.clear()
        queryParamsList.postValue(queryParamsList.value)
        return dbRepository.clearHistory()
    }

    fun loadHistory(): Single<List<QueryParams>> {
        return dbRepository.getHistoryQueries()
                .doOnSuccess {
                    queryParamsList.value?.addAll(it)
                    queryParamsList.postValue(queryParamsList.value)
                }
    }
}
