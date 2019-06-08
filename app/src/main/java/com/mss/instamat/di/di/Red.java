package com.mss.instamat.di.di;

import android.util.Log;

public class Red {

    Green green;

    public Red(Green green) {
        this.green = green;
        Log.d("TAG", green.show());
        Log.d("TAG", green.toString());
    }
}
