package com.mss.instamat.repositories.db;

import com.mss.instamat.model.CacheDBRepository;
import com.mss.instamat.model.models.ImageInfo;
import com.mss.instamat.repositories.db.models.ImageInfoDB;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Single;

public class CacheDBRepositoryImpl implements CacheDBRepository {

    private final CacheDB cacheDB;

    public CacheDBRepositoryImpl(CacheDB cacheDB) {
        this.cacheDB = cacheDB;
    }

    @Override
    public Single<List<Long>> saveToCacheDB(String searchText, int page, List<ImageInfo> images) {
        return Single.create(emitter -> emitter.onSuccess(cacheDB.productDao().insertAll(mapToDB(searchText, page, images))));
    }

    private List<ImageInfoDB> mapToDB(String searchText, int page, List<ImageInfo> images) {
        return images.stream().map(imageInfo -> map(searchText, page, imageInfo)).collect(Collectors.toList());
    }

    private ImageInfoDB map(String searchText, int page, ImageInfo imageInfo) {
        ImageInfoDB imageInfoDB = new ImageInfoDB();
        imageInfoDB.setId(imageInfo.getId());
        imageInfoDB.setLargeImageURL(imageInfo.getLargeImageURL());
        imageInfoDB.setPreviewURL(imageInfo.getPreviewURL());
        imageInfoDB.setQuery(searchText);
        imageInfoDB.setPage(page);
        return imageInfoDB;
    }
}
