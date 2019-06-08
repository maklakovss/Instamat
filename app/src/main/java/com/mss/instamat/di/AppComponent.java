package com.mss.instamat.di;


import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.view.imagelist.ImageListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(ImageListModel imageListModel);

    void inject(ImageListActivity imageListActivity);
}