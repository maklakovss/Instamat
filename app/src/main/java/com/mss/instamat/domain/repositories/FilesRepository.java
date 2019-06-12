package com.mss.instamat.domain.repositories;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.mss.instamat.domain.models.ImageInfo;

import java.io.IOException;

public interface FilesRepository {

    @NonNull
    String saveBitmap(@NonNull final ImageInfo imageInfo, @NonNull final Bitmap bitmap) throws IOException;
}
