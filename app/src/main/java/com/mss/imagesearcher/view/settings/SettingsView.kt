package com.mss.imagesearcher.view.settings

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.mss.imagesearcher.model.entity.QueryParams

interface SettingsView : MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showQuery(queryParams: QueryParams?)

}
