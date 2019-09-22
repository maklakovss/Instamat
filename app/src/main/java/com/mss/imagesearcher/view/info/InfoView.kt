package com.mss.imagesearcher.view.info

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.mss.imagesearcher.domain.models.ImageInfo

interface InfoView : MvpView {

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun initAdMob()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showFullScreenAd()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showInfo(imageInfo: ImageInfo)
}
