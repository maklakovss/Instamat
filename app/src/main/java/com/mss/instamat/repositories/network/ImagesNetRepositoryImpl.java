package com.mss.instamat.repositories.network;

import android.support.annotation.NonNull;

import com.mss.instamat.domain.models.ImageInfo;
import com.mss.instamat.domain.repositories.ImagesNetRepository;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ImagesNetRepositoryImpl implements ImagesNetRepository {

    private static final String KEY = "12657704-170e4200b7841bc79a107ea6e";
    private static final String LANG = "ru";
    private static final String IMAGE_TYPE = "photo";
    private static final int IMAGES_PER_PAGE = 50;

    private final PixabayAPI pixabayAPI;

    public ImagesNetRepositoryImpl(@NonNull final PixabayAPI pixabayAPI) {
        this.pixabayAPI = pixabayAPI;
    }

    @NonNull
    @Override
    public Maybe<List<ImageInfo>> findImages(@NonNull final String query, int pageNumber) {
        return pixabayAPI
                .findImages(KEY, query, LANG, IMAGE_TYPE, pageNumber, IMAGES_PER_PAGE)
                .doOnSubscribe(disposable -> Timber.d("Start get images from network '%s' page %d", query, pageNumber))
                .doOnSuccess(imagesResponse -> Timber.d("End get images %d from network '%s' page %d", imagesResponse.getHits().size(), query, pageNumber))
                .map(imagesResponse -> NetMapper.mapFromNet(imagesResponse.getHits()))
                .subscribeOn(Schedulers.io());
    }
}
