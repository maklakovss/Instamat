package com.mss.instamat.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MockModule.class})
public interface RobolectricComponent extends AppComponent {
}
