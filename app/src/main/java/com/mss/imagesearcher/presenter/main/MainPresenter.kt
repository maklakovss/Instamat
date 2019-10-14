package com.mss.imagesearcher.presenter.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.view.main.MainActivityView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(val model: ImageListModel) : MvpPresenter<MainActivityView>() {

    fun onCreate() {
        viewState.initAdMob()
    }

    fun onPrivacyPolicyClick() {
        viewState.showPrivacyPolicy()
    }

    fun onSearchClick(searchText: String) {
        Timber.d("onSearchClick")
        model.setCurrentQuery(searchText)
        viewState.goToList()
    }
}