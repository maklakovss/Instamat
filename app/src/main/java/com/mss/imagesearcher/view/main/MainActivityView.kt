package com.mss.imagesearcher.view.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleTagStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainActivityView : MvpView {

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun initAdMob()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showPrivacyPolicy()

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "ActiveFragment")
    fun showHistory()

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "ActiveFragment")
    fun showSettings()

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "ActiveFragment")
    fun showList()

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "ActiveFragment")
    fun showImage()

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "ActiveFragment")
    fun showInfo()

    @StateStrategyType(value = SkipStrategy::class)
    fun goToList()

}