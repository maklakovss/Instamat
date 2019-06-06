package com.mss.instamat.room;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface RoomView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showUsers(String users);
}
