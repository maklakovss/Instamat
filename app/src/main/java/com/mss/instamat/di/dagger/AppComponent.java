package com.mss.instamat.di.dagger;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(Red red);

    void inject(White white);
}
