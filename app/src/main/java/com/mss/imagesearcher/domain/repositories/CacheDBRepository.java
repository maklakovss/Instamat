package com.mss.imagesearcher.domain.repositories;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.domain.models.ImageInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface CacheDBRepository {

    @NonNull
    Single<List<Long>> insertToCacheDB(@NonNull final String searchText,
                                       int page,
                                       @NonNull final List<ImageInfo> images);

    @NonNull
    Single<List<ImageInfo>> getImagesInfo(@NonNull final String searchText, int page);

    @NonNull
    Completable deleteImages(@NonNull final String searchText);
}
