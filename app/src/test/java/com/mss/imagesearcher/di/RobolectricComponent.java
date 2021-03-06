package com.mss.imagesearcher.di;

import com.mss.imagesearcher.view.detail.DetailActivityTest;
import com.mss.imagesearcher.view.imagelist.ImageListActivityTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MockModule.class})
public interface RobolectricComponent extends AppComponent {

    void inject(DetailActivityTest detailActivityTest);

    void inject(ImageListActivityTest imageListActivityTest);
}
