package com.mss.instamat.view.imagelist;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

public interface ImageListView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void initImageList(@NonNull List<Integer> images);

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void openDetailActivity(int position);
}
