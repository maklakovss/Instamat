package com.mss.imagesearcher.domain.repositories;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.domain.models.ImageInfo;

import java.io.IOException;

public interface FilesRepository {

    @NonNull
    String saveBitmap(@NonNull final ImageInfo imageInfo, @NonNull final Bitmap bitmap) throws IOException;
}
