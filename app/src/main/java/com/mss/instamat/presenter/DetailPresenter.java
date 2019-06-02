package com.mss.instamat.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.view.detail.DetailView;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private final ImageListModel model = ImageListModel.getInstance();

    public void onCreate(int position) {
        getViewState().showProgress(true);
        getViewState().showImage(model.getImagesResponse().getHits().get(position).getLargeImageURL());
    }

    public void onImageLoaded() {
        getViewState().showProgress(false);
    }
}
