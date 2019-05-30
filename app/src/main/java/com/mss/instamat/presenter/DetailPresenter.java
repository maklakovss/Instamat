package com.mss.instamat.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.view.detail.DetailView;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private static String TAG = "DetailPresenter";

    private final ImageListModel model = ImageListModel.getInstance();

    public void onCreate(int position) {
        Log.d(TAG, String.valueOf(position));
        getViewState().showImage(model.getImages().get(position));
    }
}
