package com.mss.instamat.repositories.files;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.mss.instamat.domain.models.ImageInfo;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import timber.log.Timber;

public class FilesHelper {

    private static final String SAVE_FOLDER = "instamat";

    @NotNull
    public String getFolderPath() {
        return getPublicDirectory() + File.separator + SAVE_FOLDER;
    }

    @NotNull
    public String getFilePath(String folder, @NonNull ImageInfo imageInfo) {
        return folder + File.separator + imageInfo.getId() + ".jpg";
    }

    public void createFolderIfNotExist(@NonNull String folder) {
        new File(folder).mkdirs();
    }

    @NotNull
    public OutputStream getFileOutputStream(String filePath) throws IOException {
        return new FileOutputStream(filePath);
    }

    public void deleteFileIfExist(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Timber.d("Delete file in %s", filePath);
            file.delete();
        }
    }

    public String getPublicDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    public void saveBitmapToStream(@NonNull Bitmap bitmap, OutputStream outStream) throws IOException {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();
    }
}
