package com.mss.instamat.di.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    @Singleton
    @Provides
    Green provideGreen() {
        return new Green();
    }
}
