package com.mss.instamat.network;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface RetrofitView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showAvatar(String avatarUrl);
}
