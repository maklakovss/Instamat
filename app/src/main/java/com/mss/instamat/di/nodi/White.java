package com.mss.instamat.di.nodi;

import android.util.Log;

public class White {

    Green green;

    public White() {
        green = new Green();
        Log.d("TAG", green.show());
        Log.d("TAG", green.toString());
    }
}
