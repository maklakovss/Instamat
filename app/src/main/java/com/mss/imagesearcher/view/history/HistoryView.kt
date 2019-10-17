package com.mss.imagesearcher.view.history

import com.arellomobile.mvp.MvpView
import com.mss.imagesearcher.model.entity.QueryParams

interface HistoryView : MvpView {

    fun refreshHistoryList(value: List<QueryParams>?)

}
