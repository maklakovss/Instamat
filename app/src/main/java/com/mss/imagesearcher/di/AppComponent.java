package com.mss.imagesearcher.di;


import androidx.annotation.NonNull;

import com.mss.imagesearcher.view.detail.DetailActivity;
import com.mss.imagesearcher.view.imagelist.ImageListAdapter;
import com.mss.imagesearcher.view.info.InfoActivity;
import com.mss.imagesearcher.view.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DBModule.class, NetworkModule.class, FilesModule.class})
public interface AppComponent {

    void inject(@NonNull final MainActivity mainActivity);

    void inject(@NonNull final DetailActivity detailActivity);

    void inject(@NonNull final ImageListAdapter imageListAdapter);

    void inject(@NonNull final InfoActivity infoActivity);
}
