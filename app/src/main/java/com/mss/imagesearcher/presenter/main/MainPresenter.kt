package com.mss.imagesearcher.presenter.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.view.main.MainActivityView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(val model: ImageListModel) : MvpPresenter<MainActivityView>() {

    init {
        model.needShowPage.observeForever {
            if (it == ImageListModel.PageType.NONE) {
                return@observeForever
            }
            when (it) {
                ImageListModel.PageType.HISTORY -> viewState.goToHistory()
                ImageListModel.PageType.LIST -> viewState.goToList()
                ImageListModel.PageType.DETAIL -> viewState.goToDetail()
                else -> return@observeForever
            }
            model.needShowPage.value = ImageListModel.PageType.NONE
        }
    }

    fun onCreate() {
        viewState.initAdMob()
    }

    fun onPrivacyPolicyClick() {
        viewState.showPrivacyPolicy()
    }

    fun onSearchClick(searchText: String) {
        Timber.d("onSearchClick")
        model.setCurrentQuery(searchText)
        model.needShowPage.value = ImageListModel.PageType.LIST
    }
}