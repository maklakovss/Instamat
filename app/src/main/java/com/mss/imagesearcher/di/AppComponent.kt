package com.mss.imagesearcher.di


import com.mss.imagesearcher.view.detail.DetailFragment
import com.mss.imagesearcher.view.history.HistoryFragment
import com.mss.imagesearcher.view.imagelist.ImageListAdapter
import com.mss.imagesearcher.view.imagelist.ImageListFragment
import com.mss.imagesearcher.view.info.InfoFragment
import com.mss.imagesearcher.view.main.MainActivity
import com.mss.imagesearcher.view.settings.SettingsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, FilesModule::class, DBModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(detailFragment: DetailFragment)

    fun inject(imageListAdapter: ImageListAdapter)

    fun inject(infoFragment: InfoFragment)

    fun inject(imageListFragment: ImageListFragment)

    fun inject(historyFragment: HistoryFragment)

    fun inject(settingsFragment: SettingsFragment)
}
