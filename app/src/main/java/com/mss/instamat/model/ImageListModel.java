package com.mss.instamat.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mss.instamat.model.models.ImageInfo;
import com.mss.instamat.model.models.ImagesResponse;
import com.mss.instamat.repositories.network.ImagesRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

public class ImageListModel {

    private static final ImageListModel instance = new ImageListModel();

    private final ImagesRepository imagesRepository;
    private final List<ImageInfo> imageInfoList = new ArrayList<>();

    public ImageListModel() {
        imagesRepository = new ImagesRepository();
    }

    @NonNull
    public static ImageListModel getInstance() {
        return instance;
    }

    @NonNull
    public Maybe<ImagesResponse> getImagesFromNetwork(String searchText, int page) {
        return imagesRepository.findImages(searchText, page)
                .doOnSuccess(imagesResponse -> imageInfoList.addAll(imagesResponse.getHits()))
                .doOnError(throwable -> Log.d("", throwable.toString()));
    }

    public List<ImageInfo> getImages() {
        return imageInfoList;
    }

    public void clearImages() {
        imageInfoList.clear();
    }
}
