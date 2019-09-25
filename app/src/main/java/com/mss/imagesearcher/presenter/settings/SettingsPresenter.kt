package com.mss.imagesearcher.presenter.settings

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.view.settings.SettingsView

@InjectViewState
class SettingsPresenter(private val model: ImageListModel) : MvpPresenter<SettingsView>() {

}
