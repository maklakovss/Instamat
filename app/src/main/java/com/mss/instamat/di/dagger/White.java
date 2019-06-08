package com.mss.instamat.di.dagger;

import android.util.Log;

import javax.inject.Inject;

public class White {

    @Inject
    Green green;

    public White() {
        App.getAppComponent().inject(this);
        Log.d("TAG", green.show());
        Log.d("TAG", green.toString());
    }
}
