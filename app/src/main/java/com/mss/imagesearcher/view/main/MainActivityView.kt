package com.mss.imagesearcher.view.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainActivityView : MvpView {

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun initAdMob()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showPrivacyPolicy()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun goToList()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun goToDetail()
}