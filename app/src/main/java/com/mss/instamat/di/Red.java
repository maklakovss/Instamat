package com.mss.instamat.di;

import android.util.Log;

public class Red {

    public Red() {
        Green green = new Green();
        Log.d("TAG", green.show());
        Log.d("TAG", green.toString());
    }
}
