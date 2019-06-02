package com.mss.instamat.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

public class ImageListModel {

    private static final ImageListModel instance = new ImageListModel();

    private final ImagesRepository imagesRepository;
    private final List<ImageInfo> imageInfoList = new ArrayList<>();
    private String lastQuery = "";
    private int lastPage = 0;

    public ImageListModel() {
        imagesRepository = new ImagesRepository();
    }

    @NonNull
    public static ImageListModel getInstance() {
        return instance;
    }

    @NonNull
    public Maybe<ImagesResponse> findImages(String searchText) {
        if (!lastQuery.equals(searchText)) {
            lastPage = 0;
            imageInfoList.clear();
            lastQuery = searchText;
        }
        return imagesRepository.findImages(searchText, ++lastPage, 50)
                .doOnSuccess(imagesResponse -> imageInfoList.addAll(imagesResponse.getHits()))
                .doOnError(throwable -> Log.d("", throwable.toString()));
    }

    public List<ImageInfo> getImages() {
        return imageInfoList;
    }
}
