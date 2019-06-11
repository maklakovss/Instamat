package com.mss.instamat.repositories.files;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.mss.instamat.domain.models.ImageInfo;
import com.mss.instamat.domain.repositories.FilesRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import timber.log.Timber;

public class FilesRepositoryImpl implements FilesRepository {

    private static final String SAVE_FOLDER = "instamat";

    @NonNull
    @Override
    public String saveBitmap(@NonNull ImageInfo imageInfo, @NonNull Bitmap bitmap) throws IOException {
        Timber.d("Save bitmap '%s' started", imageInfo.getId());
        final File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        final File myDir = new File(storageLoc.getAbsoluteFile() + File.separator + SAVE_FOLDER);
        myDir.mkdirs();
        final File file = new File(myDir, imageInfo.getId() + ".jpg");
        if (file.exists()) {
            Timber.d("Delete bitmap '%s' in %s", imageInfo.getId(), file.getAbsolutePath());
            file.delete();
        }
        file.createNewFile();
        final FileOutputStream outStream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();
        Timber.d("Done save bitmap '%s' to %s", imageInfo.getId(), file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}
