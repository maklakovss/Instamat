package com.mss.instamat.di.nodi;

import android.util.Log;

public class Red {

    public Red() {
        Green green = new Green();
        Log.d("TAG", green.show());
        Log.d("TAG", green.toString());
    }
}
