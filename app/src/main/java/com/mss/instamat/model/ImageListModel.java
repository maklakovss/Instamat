package com.mss.instamat.model;

import android.support.annotation.NonNull;
import android.util.Log;

import io.reactivex.Maybe;

public class ImageListModel {

    private static final ImageListModel instance = new ImageListModel();

    private final ImagesRepository imagesRepository;
    private ImagesResponse imagesResponse;

    public ImageListModel() {
        imagesRepository = new ImagesRepository();
    }

    @NonNull
    public static ImageListModel getInstance() {
        return instance;
    }

    @NonNull
    public Maybe<ImagesResponse> findImages(String searchText) {
        return imagesRepository.findImages(searchText, 1, 50)
                .doOnSuccess(i -> {
                    imagesResponse = i;
                })
                .doOnError(throwable -> {
                    Log.d("", throwable.toString());
                });
    }

    public ImagesResponse getImagesResponse() {
        return imagesResponse;
    }
}
