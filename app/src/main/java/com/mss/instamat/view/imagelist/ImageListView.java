package com.mss.instamat.view.imagelist;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ImageListView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void initImageList();

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void openDetailActivity(int position);
}
