package com.mss.instamat.repositories.db;

import android.support.annotation.NonNull;

import com.mss.instamat.domain.CacheDBRepository;
import com.mss.instamat.domain.models.ImageInfo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CacheDBRepositoryImpl implements CacheDBRepository {

    private final CacheDB cacheDB;

    @Inject
    public CacheDBRepositoryImpl(@NonNull final CacheDB cacheDB) {
        this.cacheDB = cacheDB;
    }

    @Override
    @NonNull
    public Single<List<Long>> saveToCacheDB(@NonNull final String searchText,
                                            int page,
                                            @NonNull final List<ImageInfo> images) {
        return Single.create(emitter -> {
            cacheDB.productDao().deleteQuery(searchText, page);
            emitter.onSuccess(cacheDB
                    .productDao()
                    .insertAll(DBMapper.mapToDB(searchText, page, images)));
        });
    }

    @Override
    @NonNull
    public Single<List<ImageInfo>> getImagesInfo(@NonNull final String searchText, int page) {
        return cacheDB
                .productDao()
                .getImagesInfo(searchText, page)
                .map(imagesDB -> DBMapper.mapFromDb(imagesDB));
    }
}
