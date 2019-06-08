package com.mss.instamat.di.di;

import android.util.Log;

public class White {

    Green green;

    public White(Green green) {
        this.green = green;
        Log.d("TAG", green.show());
        Log.d("TAG", green.toString());
    }
}
