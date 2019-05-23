package com.mss.instamat.mvpmoxy;

import android.support.annotation.NonNull;

public class AddState {

    private String currentString = "";

    public String getCurrentString() {
        return currentString;
    }

    public void setCurrentString(@NonNull final String currentString) {
        this.currentString = currentString;
    }
}
