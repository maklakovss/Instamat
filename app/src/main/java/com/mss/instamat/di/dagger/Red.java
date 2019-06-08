package com.mss.instamat.di.dagger;

import android.util.Log;

import javax.inject.Inject;

public class Red {

    @Inject
    Green green;

    public Red() {
        App.getAppComponent().inject(this);
        Log.d("TAG", green.show());
        Log.d("TAG", green.toString());
    }
}
