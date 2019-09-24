package com.mss.imagesearcher.presenter.info;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.imagesearcher.model.ImageListModel;
import com.mss.imagesearcher.view.info.InfoView;

@InjectViewState
public class InfoPresenter extends MvpPresenter<InfoView> {

    private ImageListModel model;

    public InfoPresenter(ImageListModel model) {
        this.model = model;
    }

    public void onCreate(int position) {
        getViewState().showInfo(model.getImages().get(position));
        getViewState().initAdMob();
    }

    public void onAdLoaded() {
        getViewState().showFullScreenAd();
    }
}
