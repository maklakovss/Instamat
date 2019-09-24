package com.mss.imagesearcher.view.detail

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface DetailView : MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun startLoadImage(imageURL: String)

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showProgress(visible: Boolean)

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showSuccessSaveMessage()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showFailedSaveMessage()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun shareImage(path: String)

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showImage(visible: Boolean)

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showInfo()
}
