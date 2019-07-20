package com.mss.imagesearcher.view.imagelist;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ImageListView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshImageList();

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void openDetailActivity(int position);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showProgress(boolean visible);

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showNotFoundMessage();

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void stopRefreshing();
}
