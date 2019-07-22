package com.mss.imagesearcher.domain;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.domain.repositories.CacheDBRepository;
import com.mss.imagesearcher.domain.repositories.FilesRepository;
import com.mss.imagesearcher.domain.repositories.ImagesNetRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import timber.log.Timber;

public class ImageListModel {

    private final List<ImageInfo> imageInfoList;
    private final CacheDBRepository cacheDBRepository;
    private final ImagesNetRepository imagesNetRepository;
    private final FilesRepository filesRepository;

    @Inject
    public ImageListModel(@NonNull final CacheDBRepository cacheDBRepository,
                          @NonNull final ImagesNetRepository imagesNetRepository, FilesRepository filesRepository) {
        imageInfoList = new ArrayList<>();
        this.cacheDBRepository = cacheDBRepository;
        this.imagesNetRepository = imagesNetRepository;
        this.filesRepository = filesRepository;
    }

    @NonNull
    public Maybe<List<ImageInfo>> getImagesFromNetwork(@NonNull final String searchText, int page) {
        return imagesNetRepository.findImages(searchText, page)
                .doOnSuccess(images -> {
                    imageInfoList.addAll(images);
                    Timber.d("Added from network %d images on query '%s' page %d, all - %d",
                            images.size(),
                            searchText,
                            page,
                            imageInfoList.size());
                })
                .doOnError(Timber::e);
    }

    @NonNull
    public List<ImageInfo> getImages() {
        return imageInfoList;
    }

    public void clearImages() {
        imageInfoList.clear();
        Timber.d("Image list cleared");
    }

    @NonNull
    public Single<List<ImageInfo>> getImagesFromCacheDB(@NonNull final String searchText, int page) {
        return cacheDBRepository.getImagesInfo(searchText, page)
                .doOnSuccess(images -> {
                    imageInfoList.addAll(images);
                    Timber.d("Added from cache database %d images on query '%s' page %d, all - %d",
                            images.size(),
                            searchText,
                            page,
                            imageInfoList.size());
                })
                .doOnError(Timber::e);
    }

    @NonNull
    public Single<List<Long>> saveToCacheDBAsync(@NonNull final String searchText,
                                                 int page,
                                                 @NonNull final List<ImageInfo> images) {
        return cacheDBRepository
                .insertToCacheDB(searchText, page, images)
                .doOnSuccess(list ->
                        Timber.d("Saved to cache database %d images on query '%s' page %d",
                                list.size(),
                                searchText,
                                page))
                .doOnError(Timber::e);
    }

    @NonNull
    public String saveBitmap(ImageInfo imageInfo, @NonNull final Bitmap bitmap) throws IOException {
        final String path = filesRepository.saveBitmap(imageInfo, bitmap);
        Timber.d("Image saved to path - %s", path);
        return path;
    }

    public Completable deleteImagesFromCache(String searchText) {
        return cacheDBRepository.deleteImages(searchText);
    }
}
