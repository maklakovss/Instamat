package com.mss.imagesearcher.di

import com.mss.imagesearcher.view.detail.DetailFragmentTest
import com.mss.imagesearcher.view.imagelist.MainActivityTest
import com.mss.imagesearcher.view.info.InfoFragmentTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MockModule::class])
interface RobolectricComponent : AppComponent {

    fun inject(detailActivityTest: DetailFragmentTest)

    fun inject(imageListActivityTest: MainActivityTest)

    fun inject(infoActivityTest: InfoFragmentTest)
}
