package com.mss.imagesearcher.model

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
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

    val _currentQuery = MutableLiveData<QueryParams>()
    val currentQuery: LiveData<QueryParams> get() = _currentQuery

    val currentImage = MutableLiveData<ImageInfo>(null)
    val needShowPage = MutableLiveData<PageType>(PageType.HISTORY)
    val queryParamsList = MutableLiveData<MutableList<QueryParams>>(arrayListOf())

    enum class PageType {
        NONE, HISTORY, SETTINGS, LIST, DETAIL, INFO
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

    fun setCurrentQuery(queryParams: QueryParams) {
        _currentQuery.value = queryParams
    }

    fun clearHistoryInDB(): Single<Int> {
        return dbRepository.clearHistory()
    }

    fun loadHistoryFromDB(): Single<List<QueryParams>> {
        return dbRepository.getHistoryQueries()
    }

    fun clearHistoryInMemory() {
        queryParamsList.value?.clear()
        queryParamsList.value = queryParamsList.value

    }

    fun setHistoryInMemory(list: List<QueryParams>) {
        queryParamsList.value?.clear()
        queryParamsList.value?.addAll(list)
        queryParamsList.value = queryParamsList.value

    }

    fun saveQueryParamsToDB(queryParams: QueryParams): Single<Long> {
        return dbRepository.addQueryInHistory(queryParams)
    }

    fun popParamQueryToListInMemory(queryParams: QueryParams) {
        queryParamsList.value?.let { list ->
            list.removeAll(list.filter { it.equals(queryParams) })
            list.add(0, queryParams)
        }
        queryParamsList.value = queryParamsList.value
    }
}
