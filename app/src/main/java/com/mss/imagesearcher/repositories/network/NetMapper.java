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
        imageInfo.setType(imageInfoDB.getType());
        imageInfo.setTags(imageInfoDB.getTags());
        imageInfo.setPageUrl(imageInfoDB.getPageUrl());
        imageInfo.setLargeImageURL(imageInfoDB.getLargeImageURL());
        imageInfo.setWebFormatURL(imageInfoDB.getWebFormatURL());
        imageInfo.setPreviewURL(imageInfoDB.getPreviewURL());
        imageInfo.setComments(imageInfoDB.getComments());
        imageInfo.setLikes(imageInfoDB.getLikes());
        imageInfo.setViews(imageInfoDB.getViews());
        imageInfo.setHeight(imageInfoDB.getHeight());
        imageInfo.setWidth(imageInfoDB.getWidth());
        return imageInfo;
    }
}
