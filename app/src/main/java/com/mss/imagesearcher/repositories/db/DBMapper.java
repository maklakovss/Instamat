package com.mss.imagesearcher.repositories.db;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.repositories.db.models.ImageInfoDB;

import java.util.List;
import java.util.stream.Collectors;

public class DBMapper {

    @NonNull
    public static List<ImageInfo> mapFromDb(@NonNull final List<ImageInfoDB> imagesDB) {
        return imagesDB
                .stream()
                .map(imageInfoDB -> mapFromDB(imageInfoDB))
                .collect(Collectors.toList());
    }

    @NonNull
    public static ImageInfo mapFromDB(@NonNull final ImageInfoDB imageInfoDB) {
        final ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(imageInfoDB.getId());
        imageInfo.setLargeImageURL(imageInfoDB.getLargeImageURL());
        imageInfo.setPreviewURL(imageInfoDB.getPreviewURL());
        return imageInfo;
    }

    @NonNull
    public static List<ImageInfoDB> mapToDB(@NonNull final String searchText,
                                            int page,
                                            @NonNull final List<ImageInfo> images) {
        return images
                .stream()
                .map(imageInfo -> mapToDB(searchText, page, imageInfo))
                .collect(Collectors.toList());
    }

    @NonNull
    public static ImageInfoDB mapToDB(@NonNull final String searchText,
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
