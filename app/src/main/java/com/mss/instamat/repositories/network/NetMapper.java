package com.mss.instamat.repositories.network;

import android.support.annotation.NonNull;

import com.mss.instamat.domain.models.ImageInfo;
import com.mss.instamat.repositories.network.models.ImageInfoNet;

import java.util.List;
import java.util.stream.Collectors;

class NetMapper {

    @NonNull
    public static List<ImageInfo> mapFromNet(@NonNull final List<ImageInfoNet> imagesNet) {
        return imagesNet
                .stream()
                .map(imageInfoDB -> mapFromNet(imageInfoDB))
                .collect(Collectors.toList());
    }

    @NonNull
    private static ImageInfo mapFromNet(@NonNull final ImageInfoNet imageInfoDB) {
        final ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(imageInfoDB.getId());
        imageInfo.setLargeImageURL(imageInfoDB.getLargeImageURL());
        imageInfo.setPreviewURL(imageInfoDB.getPreviewURL());
        return imageInfo;
    }
}
