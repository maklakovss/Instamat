package com.mss.imagesearcher.di


import com.mss.imagesearcher.view.detail.DetailFragment
import com.mss.imagesearcher.view.imagelist.ImageListAdapter
import com.mss.imagesearcher.view.imagelist.ImageListFragment
import com.mss.imagesearcher.view.info.InfoActivity
import com.mss.imagesearcher.view.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, FilesModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(detailFragment: DetailFragment)

    fun inject(imageListAdapter: ImageListAdapter)

    fun inject(infoActivity: InfoActivity)

    fun inject(imageListFragment: ImageListFragment)
}
