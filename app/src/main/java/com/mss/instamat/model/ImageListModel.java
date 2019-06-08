package com.mss.instamat.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mss.instamat.model.models.ImageInfo;
import com.mss.instamat.model.models.ImagesResponse;
import com.mss.instamat.repositories.network.ImagesRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class ImageListModel {

    private final List<ImageInfo> imageInfoList;
    private final CacheDBRepository cacheDBRepository;
    private final ImagesRepository imagesRepository;

    @Inject
    public ImageListModel(CacheDBRepository cacheDBRepository, ImagesRepository imagesRepository) {
        imageInfoList = new ArrayList<>();
        this.cacheDBRepository = cacheDBRepository;
        this.imagesRepository = imagesRepository;
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

    public Single<List<ImageInfo>> getImagesFromCacheDB(String searchText, int page) {
        return Single.just(new ArrayList<>());
    }

    public void saveToCacheDBAsync(String searchText, int page, List<ImageInfo> images) {
        cacheDBRepository
                .saveToCacheDB(searchText, page, images)
                .subscribe(list -> Log.d("", "Saved " + list.size() + "records to cache DB"),
                        throwable -> Log.e("", "Filed save records to cache DB", throwable));
    }
}
