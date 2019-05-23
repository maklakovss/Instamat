package com.mss.instamat.ui.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.R;
import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.ui.view.ImageListView;

@InjectViewState
public class ImageListPresenter extends MvpPresenter<ImageListView> {

    private ImageListModel model = new ImageListModel();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        for (int i = 0; i < 40; i++) {
            model.getImages().add(R.drawable.ic_android_black_24dp);
        }
        getViewState().initImageList(model.getImages());
    }

    public void onItemClick(int position) {
        model.setCountClick(model.getCountClick() + 1);
        Log.d("TAG", String.valueOf(model.getCountClick()));
    }
}
