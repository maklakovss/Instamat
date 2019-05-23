package com.mss.instamat.mvpmoxy;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class AddPresenter extends MvpPresenter<AddView> {

    private final AddState state = new AddState();

    public void onButtonClick(@NonNull final String text) {
        state.setCurrentString(state.getCurrentString() + text);
        getViewState().setResultString(state.getCurrentString());
    }
}
