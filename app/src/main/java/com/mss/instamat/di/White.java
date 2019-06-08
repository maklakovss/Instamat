package com.mss.instamat.di;

import android.util.Log;

public class White {

    public White() {
        Green green = new Green();
        Log.d("TAG", green.show());
        Log.d("TAG", green.toString());
    }
}
