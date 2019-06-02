package com.mss.instamat.view.detail;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface DetailView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showImage(String imageURL);

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showProgress(boolean visible);

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showSuccessSaveMessage();

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showFailedSaveMessage();
}
