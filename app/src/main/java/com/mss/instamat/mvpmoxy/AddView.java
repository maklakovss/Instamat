package com.mss.instamat.mvpmoxy;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface AddView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setResultString(@NonNull final String currentString);
}
