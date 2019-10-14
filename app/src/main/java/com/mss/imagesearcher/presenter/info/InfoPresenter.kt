package com.mss.imagesearcher.presenter.info

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.view.info.InfoView

@InjectViewState
class InfoPresenter(private val model: ImageListModel) : MvpPresenter<InfoView>() {

    fun onCreate() {
        model.currentImage.value?.let {
            viewState.showInfo(it)
        }
    }
}
