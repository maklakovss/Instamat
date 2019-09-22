package com.mss.imagesearcher.di

import com.mss.imagesearcher.view.detail.DetailActivityTest
import com.mss.imagesearcher.view.imagelist.MainActivityTest
import com.mss.imagesearcher.view.info.InfoActivityTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MockModule::class])
interface RobolectricComponent : AppComponent {

    fun inject(detailActivityTest: DetailActivityTest)

    fun inject(imageListActivityTest: MainActivityTest)

    fun inject(infoActivityTest: InfoActivityTest)
}
