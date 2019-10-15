package com.mss.imagesearcher.view.info

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleTagStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.mss.imagesearcher.model.entity.ImageInfo

interface InfoView : MvpView {

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "showInfo")
    fun showInfo(imageInfo: ImageInfo)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "showInfo")
    fun clearInfo()
}
