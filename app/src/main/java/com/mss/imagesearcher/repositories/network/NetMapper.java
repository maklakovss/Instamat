package com.mss.imagesearcher.repositories.network;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.repositories.network.models.ImageInfoNet;

import java.util.ArrayList;
import java.util.List;

class NetMapper {

    @NonNull
    public static List<ImageInfo> mapFromNet(@NonNull final List<ImageInfoNet> imagesNet) {
        List<ImageInfo> images = new ArrayList<>();
        for (ImageInfoNet imageNet : imagesNet) {
            images.add(mapFromNet(imageNet));
        }
        return images;
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
