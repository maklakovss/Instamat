package com.mss.imagesearcher.model.repositories.network

import com.mss.imagesearcher.model.entity.ImageInfo
import com.mss.imagesearcher.model.repositories.ImagesNetRepository
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class ImagesNetRepositoryImpl(private val pixabayAPI: PixabayAPI) : ImagesNetRepository {

    companion object {
        private val KEY = "12657704-170e4200b7841bc79a107ea6e"
        private val LANG_RU = "ru"
        private val LANG_EN = "en"
        private val IMAGE_TYPE = "photo"
        private val IMAGES_PER_PAGE = 50
    }

    private val lang: String
        get() {
            val language = Locale.getDefault().displayLanguage
            return if (language == "русский") {
                LANG_RU
            } else LANG_EN
        }

    override fun findImages(query: String, pageNumber: Int): Maybe<List<ImageInfo>> {
        return pixabayAPI
                .findImages(KEY, query, lang, IMAGE_TYPE, pageNumber, IMAGES_PER_PAGE)
                .doOnSubscribe { Timber.d("Start get images from network '%s' page %d", query, pageNumber) }
                .doOnSuccess { (_, _, hits) -> Timber.d("End get images %d from network '%s' page %d", hits!!.size, query, pageNumber) }
                .map { (_, _, hits) -> NetMapper.mapFromNet(hits!!) }
                .subscribeOn(Schedulers.io())
    }
}
