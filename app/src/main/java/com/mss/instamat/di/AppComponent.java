package com.mss.instamat.di;


import android.support.annotation.NonNull;

import com.mss.instamat.view.detail.DetailActivity;
import com.mss.instamat.view.imagelist.ImageListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DBModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(@NonNull final ImageListActivity imageListActivity);

    void inject(@NonNull final DetailActivity detailActivity);
}
