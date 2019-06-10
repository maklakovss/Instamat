package com.mss.instamat.domain.repositories;

import android.support.annotation.NonNull;

import com.mss.instamat.domain.models.ImageInfo;

import java.util.List;

import io.reactivex.Single;

public interface CacheDBRepository {

    @NonNull
    Single<List<Long>> saveToCacheDB(@NonNull final String searchText,
                                     int page,
                                     @NonNull final List<ImageInfo> images);

    @NonNull
    Single<List<ImageInfo>> getImagesInfo(@NonNull final String searchText, int page);
}
