package com.mss.imagesearcher.view.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mss.imagesearcher.App

import com.mss.imagesearcher.R
import com.mss.imagesearcher.presenter.settings.SettingsPresenter
import timber.log.Timber
import javax.inject.Inject

class SettingsFragment : MvpAppCompatFragment(), SettingsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onCreate")
    }

    @ProvidePresenter
    fun providePresenter(): SettingsPresenter {
        return presenter
    }
}
