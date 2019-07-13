package com.mss.imagesearcher.presenter.detail;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.imagesearcher.domain.ImageListModel;
import com.mss.imagesearcher.view.detail.DetailView;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import timber.log.Timber;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private final ImageListModel model;

    @Inject
    public DetailPresenter(@NonNull final ImageListModel model) {
        this.model = model;
    }

    public void onCreate(int position) {
        Timber.d("onCreate %d", position);
        getViewState().showProgress(true);
        getViewState().showImage(model.getImages().get(position).getLargeImageURL());
    }

    public void onImageLoaded() {
        Timber.d("onImageLoaded");
        getViewState().showProgress(false);
    }

    public void onImageLoadFailed() {
        Timber.d("onImageLoadFailed");
        getViewState().showProgress(false);
    }

    public void onSaveClick(int position, @NonNull final Bitmap bitmap) {
        try {
            Timber.d("onSaveClick");
            model.saveBitmap(model.getImages().get(position), bitmap);
            getViewState().showSuccessSaveMessage();
        } catch (FileNotFoundException e) {
            Timber.e(e);
            getViewState().showFailedSaveMessage();
        } catch (IOException e) {
            Timber.e(e);
            getViewState().showFailedSaveMessage();
        }
    }

    public void onShareClick(int position, Bitmap bitmap) {
        try {
            Timber.d("onShareClick");
            String path = model.saveBitmap(model.getImages().get(position), bitmap);
            getViewState().shareImage(path);
        } catch (FileNotFoundException e) {
            Timber.e(e);
            getViewState().showFailedSaveMessage();
        } catch (IOException e) {
            Timber.e(e);
            getViewState().showFailedSaveMessage();
        }
    }
}
