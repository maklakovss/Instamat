package com.mss.instamat.rx;

import android.util.Log;

public class User implements Observer {

    @Override
    public void newMessage(String message) {
        Log.d("USER", "newMessage " + message + " " + Thread.currentThread().getName());
    }
}
