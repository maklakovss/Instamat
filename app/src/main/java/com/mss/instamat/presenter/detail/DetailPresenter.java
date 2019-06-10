package com.mss.instamat.presenter.detail;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.domain.ImageListModel;
import com.mss.instamat.view.detail.DetailView;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private final ImageListModel model;

    @Inject
    public DetailPresenter(@NonNull final ImageListModel model) {
        this.model = model;
    }

    public void onCreate(int position) {
        getViewState().showProgress(true);
        getViewState().showImage(model.getImages().get(position).getLargeImageURL());
    }

    public void onImageLoaded() {
        getViewState().showProgress(false);
    }

    public void onImageLoadFailed() {
        getViewState().showProgress(false);
    }

    public void onSaveClick(int position, @NonNull final Bitmap bitmap) {
        try {
            model.saveBitmap(model.getImages().get(position), bitmap);
            getViewState().showSuccessSaveMessage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            getViewState().showFailedSaveMessage();
        } catch (IOException e) {
            e.printStackTrace();
            getViewState().showFailedSaveMessage();
        }
    }

    public void onShareClick(int position, Bitmap bitmap) {
        try {
            String path = model.saveBitmap(model.getImages().get(position), bitmap);
            getViewState().shareImage(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            getViewState().showFailedSaveMessage();
        } catch (IOException e) {
            e.printStackTrace();
            getViewState().showFailedSaveMessage();
        }
    }
}
