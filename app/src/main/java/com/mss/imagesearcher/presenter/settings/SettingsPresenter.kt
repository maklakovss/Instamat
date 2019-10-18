package com.mss.imagesearcher.presenter.settings

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.model.entity.QueryParams
import com.mss.imagesearcher.view.settings.SettingsView
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

@InjectViewState
class SettingsPresenter(private val model: ImageListModel) : MvpPresenter<SettingsView>() {

    init {
        model.currentQuery.observeForever {
            viewState.showQuery(it)
        }
    }

    fun updateQuery(queryParams: QueryParams) {
        model.currentQuery.value?.let {
            queryParams.query = it.query
        }
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

    fun onClearClick() {
        var queryParams = QueryParams()
        model.currentQuery.value?.let {
            queryParams.query = it.query
        }
        model.setCurrentQuery(queryParams)
    }
}
