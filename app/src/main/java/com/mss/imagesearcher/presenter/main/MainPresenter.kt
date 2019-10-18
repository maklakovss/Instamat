package com.mss.imagesearcher.presenter.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.model.entity.QueryParams
import com.mss.imagesearcher.view.main.MainActivityView
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(val model: ImageListModel) : MvpPresenter<MainActivityView>() {

    init {
        model.needShowPage.observeForever {
            when (it) {
                ImageListModel.PageType.HISTORY -> viewState.goToHistory()
                ImageListModel.PageType.SETTINGS -> viewState.goToSettings()
                ImageListModel.PageType.LIST -> viewState.goToList()
                ImageListModel.PageType.DETAIL -> viewState.goToDetail()
                ImageListModel.PageType.INFO -> viewState.goToInfo()
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
        var queryParams: QueryParams? = model.currentQuery.value
        if (queryParams == null) {
            queryParams = QueryParams()
        }
        queryParams.query = searchText
        model.setCurrentQuery(queryParams)
        model.saveQueryParamsToDB(queryParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            model.popParamQueryToListInMemory(queryParams)
                            model.needShowPage.value = ImageListModel.PageType.LIST
                        },
                        { Timber.e(it) }
                )
    }
}