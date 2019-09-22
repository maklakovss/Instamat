package com.mss.imagesearcher

import androidx.multidex.MultiDexApplication

import com.facebook.stetho.Stetho
import com.mss.imagesearcher.di.AppComponent
import com.mss.imagesearcher.di.AppModule
import com.mss.imagesearcher.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary

import timber.log.Timber

open class App : MultiDexApplication() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initLogging()
        initAppComponent()
        initDebug()
    }

    open fun initDebug() {
        initLeakCanary()
        initStetho()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (!LeakCanary.isInAnalyzerProcess(this)) {
                LeakCanary.install(this)
            }
        }
    }

    fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    open fun initAppComponent() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(applicationContext))
                .build()
    }
}
