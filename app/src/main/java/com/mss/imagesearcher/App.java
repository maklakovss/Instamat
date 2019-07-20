package com.mss.imagesearcher;

import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.mss.imagesearcher.di.AppComponent;
import com.mss.imagesearcher.di.AppModule;
import com.mss.imagesearcher.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class App extends MultiDexApplication {

    protected static AppComponent appComponent;

    @NonNull
    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLogging();
        initAppComponent();
        initDebug();
    }

    protected void initDebug() {
        initLeakCanary();
        initStetho();
    }

    private void initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (!LeakCanary.isInAnalyzerProcess(this)) {
                LeakCanary.install(this);
            }
        }
    }

    private void initLogging() {
        Timber.plant(new Timber.DebugTree());
    }

    protected void initAppComponent() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }
}
