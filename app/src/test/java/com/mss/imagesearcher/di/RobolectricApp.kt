package com.mss.imagesearcher.di

import com.mss.imagesearcher.App

class RobolectricApp : App() {

    override fun initAppComponent() {
        appComponent = DaggerRobolectricComponent
                .builder()
                .build()
    }

    override fun initDebug() {}
}
