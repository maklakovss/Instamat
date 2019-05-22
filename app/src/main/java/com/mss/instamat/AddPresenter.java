package com.mss.instamat;

import android.support.annotation.NonNull;

public class AddPresenter {

    private final AddState state;
    private final AddView view;

    public AddPresenter(@NonNull final AddView view) {
        this.view = view;
        state = new AddState();
    }

    public void onButtonClick(@NonNull final String text) {
        state.setCurrentString(state.getCurrentString() + text);
        view.setResultString(state.getCurrentString());
    }
}
