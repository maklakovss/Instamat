package com.mss.imagesearcher.di;

import com.mss.imagesearcher.App;

public class RobolectricApp extends App {

    @Override
    protected void initAppComponent() {
        appComponent = DaggerRobolectricComponent
                .builder()
                .build();
    }

    @Override
    protected void initDebug() {
    }
}
