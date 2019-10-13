package com.mss.imagesearcher.view.imagelist

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface ListFragmentView : MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun refreshImageList()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun openDetailActivity(position: Int)

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showNotFoundMessage()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun stopRefreshing()
}
