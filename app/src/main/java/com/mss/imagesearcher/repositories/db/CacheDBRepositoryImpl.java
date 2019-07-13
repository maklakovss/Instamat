package com.mss.imagesearcher.repositories.db;

import android.support.annotation.NonNull;

import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.domain.repositories.CacheDBRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CacheDBRepositoryImpl implements CacheDBRepository {

    private final CacheDB cacheDB;

    @Inject
    public CacheDBRepositoryImpl(@NonNull final CacheDB cacheDB) {
        this.cacheDB = cacheDB;
    }

    @Override
    @NonNull
    public Single<List<Long>> insertToCacheDB(@NonNull final String searchText,
                                              int page,
                                              @NonNull final List<ImageInfo> images) {
        Single<List<Long>> single = Single.create(emitter -> {
            Timber.d("Delete started on '%s' page %d", searchText, page);
            final int count = cacheDB.productDao().deleteQuery(searchText, page);
            Timber.d("Deleted %d rows on '%s' page %d", count, searchText, page);

            Timber.d("Insert started on '%s' page %d", searchText, page);
            List<Long> longs = cacheDB.productDao().insertAll(DBMapper.mapToDB(searchText, page, images));
            Timber.d("Inserted %d rows on '%s' page %d", longs.size(), searchText, page);

            emitter.onSuccess(longs);
        });

        return single.subscribeOn(Schedulers.io());
    }

    @Override
    @NonNull
    public Single<List<ImageInfo>> getImagesInfo(@NonNull final String searchText, int page) {
        return cacheDB
                .productDao()
                .getImagesInfo(searchText, page)
                .map(DBMapper::mapFromDb)
                .doOnSubscribe(disposable -> Timber.d("Select started on '%s' page %d", searchText, page))
                .doOnSuccess(imageInfos -> Timber.d("Selected %d rows on '%s' page %d", imageInfos.size(), searchText, page))
                .subscribeOn(Schedulers.io());
    }
}
