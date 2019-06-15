package com.mss.instamat;

import android.app.Application;
import android.support.annotation.NonNull;

import com.mss.instamat.di.AppComponent;
import com.mss.instamat.di.AppModule;
import com.mss.instamat.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class App extends Application {

    private static AppComponent appComponent;

    @NonNull
    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary();
        initLogging();
        initAppComponent();
    }

    private void initLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

    private void initLogging() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }
}
