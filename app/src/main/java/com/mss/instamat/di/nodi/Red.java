package com.mss.instamat.di.nodi;

import android.util.Log;

public class Red {

    Green green;

    public Red() {
        green = new Green();
        Log.d("TAG", green.show());
        Log.d("TAG", green.toString());
    }
}
