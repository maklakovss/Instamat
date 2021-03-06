package com.mss.imagesearcher.repositories.db;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.repositories.db.models.ImageInfoDB;

import java.util.ArrayList;
import java.util.List;

class DBMapper {

    @NonNull
    static List<ImageInfo> mapFromDb(@NonNull final List<ImageInfoDB> imagesDB) {
        final List<ImageInfo> images = new ArrayList<>();
        for (ImageInfoDB imageInfoDB : imagesDB) {
            images.add(mapFromDB(imageInfoDB));
        }
        return images;
    }

    @NonNull
    private static ImageInfo mapFromDB(@NonNull final ImageInfoDB imageInfoDB) {
        final ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(imageInfoDB.getId());
        imageInfo.setLargeImageURL(imageInfoDB.getLargeImageURL());
        imageInfo.setPreviewURL(imageInfoDB.getPreviewURL());
        return imageInfo;
    }

    @NonNull
    static List<ImageInfoDB> mapToDB(@NonNull final String searchText,
                                     int page,
                                     @NonNull final List<ImageInfo> images) {
        final List<ImageInfoDB> imagesDB = new ArrayList<>();
        for (ImageInfo imageInfo : images) {
            imagesDB.add(mapToDB(searchText, page, imageInfo));
        }
        return imagesDB;
    }

    @NonNull
    private static ImageInfoDB mapToDB(@NonNull final String searchText,
                                       int page,
                                       @NonNull final ImageInfo imageInfo) {
        final ImageInfoDB imageInfoDB = new ImageInfoDB();
        imageInfoDB.setId(imageInfo.getId());
        imageInfoDB.setLargeImageURL(imageInfo.getLargeImageURL());
        imageInfoDB.setPreviewURL(imageInfo.getPreviewURL());
        imageInfoDB.setQuery(searchText);
        imageInfoDB.setPage(page);
        return imageInfoDB;
    }

}
