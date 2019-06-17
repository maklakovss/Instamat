package com.mss.instamat.di;

import com.mss.instamat.App;

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
