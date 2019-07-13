package com.mss.imagesearcher.repositories.files;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.domain.repositories.FilesRepository;

import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;

import timber.log.Timber;

public class FilesRepositoryImpl implements FilesRepository {

    private final FilesHelper filesHelper;

    @Inject
    public FilesRepositoryImpl(FilesHelper filesHelper){
        this.filesHelper = filesHelper;
    }

    @NonNull
    @Override
    public String saveBitmap(@NonNull ImageInfo imageInfo, @NonNull Bitmap bitmap) throws IOException {
        Timber.d("Save bitmap '%s' started", imageInfo.getId());
        final String folder = filesHelper.getFolderPath();
        filesHelper.createFolderIfNotExist(folder);
        final String filePath = filesHelper.getFilePath(folder, imageInfo);
        filesHelper.deleteFileIfExist(filePath);
        final OutputStream outStream = filesHelper.getFileOutputStream(filePath);
        filesHelper.saveBitmapToStream(bitmap, outStream);
        Timber.d("Done save bitmap '%s' to %s", imageInfo.getId(), filePath);
        return filePath;
    }

}
