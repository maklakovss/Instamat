package com.mss.imagesearcher.view.info

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.mss.imagesearcher.model.entity.ImageInfo

interface InfoView : MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showInfo(imageInfo: ImageInfo)
}
