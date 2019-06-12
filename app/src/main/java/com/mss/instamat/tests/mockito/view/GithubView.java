package com.mss.instamat.tests.mockito.view;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface GithubView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showName(@NonNull String name);
}
