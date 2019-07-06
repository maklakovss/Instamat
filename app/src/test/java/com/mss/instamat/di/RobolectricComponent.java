package com.mss.instamat.di;

import com.mss.instamat.view.detail.DetailActivityTest;
import com.mss.instamat.view.imagelist.ImageListActivityTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MockModule.class})
public interface RobolectricComponent extends AppComponent {

    void inject(DetailActivityTest detailActivityTest);

    void inject(ImageListActivityTest imageListActivityTest);
}
