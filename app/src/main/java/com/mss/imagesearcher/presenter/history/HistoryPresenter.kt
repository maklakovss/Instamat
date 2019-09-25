package com.mss.imagesearcher.presenter.history

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.view.history.HistoryView

@InjectViewState
class HistoryPresenter(private val model: ImageListModel) : MvpPresenter<HistoryView>() {

}
