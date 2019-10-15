package com.mss.imagesearcher.presenter.detail

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.view.detail.DetailView
import timber.log.Timber
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject

@InjectViewState
class DetailPresenter @Inject
constructor(val model: ImageListModel) : MvpPresenter<DetailView>() {

    init {
        model.currentImage.observeForever {
            viewState.apply {
                Timber.d("current image changed")
                showImage(false)
                model.currentImage.value?.let {
                    showProgress(true)
                    startLoadImage(it.largeImageURL)
                }
            }
        }
    }

    fun onImageLoaded() {
        Timber.d("onImageLoaded")
        viewState.showProgress(false)
        viewState.showImage(true)
    }

    fun onImageLoadFailed() {
        Timber.d("onImageLoadFailed")
        viewState.showProgress(false)
    }

    fun onSaveClick(bitmap: Bitmap) {
        try {
            Timber.d("onSaveClick")
            model.saveBitmap(model.images[0], bitmap)
            viewState.showSuccessSaveMessage()
        } catch (e: FileNotFoundException) {
            Timber.e(e)
            viewState.showFailedSaveMessage()
        } catch (e: IOException) {
            Timber.e(e)
            viewState.showFailedSaveMessage()
        }

    }

    fun onShareClick(bitmap: Bitmap) {
        try {
            Timber.d("onShareClick")
            val path = model.saveBitmap(model.images[0], bitmap)
            viewState.shareImage(path)
        } catch (e: FileNotFoundException) {
            Timber.e(e)
            viewState.showFailedSaveMessage()
        } catch (e: IOException) {
            Timber.e(e)
            viewState.showFailedSaveMessage()
        }
    }
}
