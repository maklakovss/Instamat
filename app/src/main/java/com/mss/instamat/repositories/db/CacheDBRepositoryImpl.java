package com.mss.instamat.repositories.db;

import com.mss.instamat.model.CacheDBRepository;
import com.mss.instamat.model.models.ImageInfo;
import com.mss.instamat.repositories.db.models.ImageInfoDB;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Single;

public class CacheDBRepositoryImpl implements CacheDBRepository {

    private final CacheDB cacheDB;

    @Inject
    public CacheDBRepositoryImpl(CacheDB cacheDB) {
        this.cacheDB = cacheDB;
    }

    @Override
    public Single<List<Long>> saveToCacheDB(String searchText, int page, List<ImageInfo> images) {
        return Single.create(emitter -> {
                    cacheDB.productDao().deleteQuery(searchText, page);
                    emitter.onSuccess(cacheDB
                            .productDao()
                            .insertAll(mapToDB(searchText, page, images)));
                }
        );
    }

    @Override
    public Single<List<ImageInfo>> getImagesInfo(String searchText, int page) {
        return cacheDB
                .productDao()
                .getImagesInfo(searchText, page)
                .map(imagesDB -> mapFromDb(imagesDB));
    }

    private List<ImageInfo> mapFromDb(List<ImageInfoDB> imagesDB) {
        return imagesDB
                .stream()
                .map(imageInfoDB -> mapFromDB(imageInfoDB))
                .collect(Collectors.toList());
    }

    private ImageInfo mapFromDB(ImageInfoDB imageInfoDB) {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(imageInfoDB.getId());
        imageInfo.setLargeImageURL(imageInfoDB.getLargeImageURL());
        imageInfo.setPreviewURL(imageInfoDB.getPreviewURL());
        return imageInfo;
    }

    private List<ImageInfoDB> mapToDB(String searchText, int page, List<ImageInfo> images) {
        return images
                .stream()
                .map(imageInfo -> mapToDB(searchText, page, imageInfo))
                .collect(Collectors.toList());
    }

    private ImageInfoDB mapToDB(String searchText, int page, ImageInfo imageInfo) {
        ImageInfoDB imageInfoDB = new ImageInfoDB();
        imageInfoDB.setId(imageInfo.getId());
        imageInfoDB.setLargeImageURL(imageInfo.getLargeImageURL());
        imageInfoDB.setPreviewURL(imageInfo.getPreviewURL());
        imageInfoDB.setQuery(searchText);
        imageInfoDB.setPage(page);
        return imageInfoDB;
    }
}
