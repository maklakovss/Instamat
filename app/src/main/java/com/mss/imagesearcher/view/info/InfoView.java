package com.mss.imagesearcher.view.info;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mss.imagesearcher.domain.models.ImageInfo;

public interface InfoView extends MvpView {

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void initAdMob();

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showFullScreenAd();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showInfo(@NonNull final ImageInfo imageInfo);
}
