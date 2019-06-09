package com.mss.instamat.model;

import com.mss.instamat.model.models.ImageInfo;

import java.util.List;

import io.reactivex.Single;

public interface CacheDBRepository {
    Single<List<Long>> saveToCacheDB(String searchText, int page, List<ImageInfo> images);

    Single<List<ImageInfo>> getImagesInfo(String searchText, int page);
}
