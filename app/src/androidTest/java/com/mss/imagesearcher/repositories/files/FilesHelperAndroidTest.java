package com.mss.imagesearcher.repositories.files;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import com.mss.imagesearcher.domain.models.ImageInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class FilesHelperAndroidTest {

    private FilesHelper filesHelper;

    @Before
    public void setUp() {
        filesHelper = new FilesHelper();
    }

    @Test
    public void getFolderPath_returnPublicPicturesInstamatPath() {
        String path = filesHelper.getFolderPath();

        assertEquals("/storage/emulated/0/Pictures/imagesearcher", path);
    }

    @Test
    public void getFilePath_returnPathToJpg() {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(1);

        String filePath = filesHelper.getFilePath("folder", imageInfo);

        assertEquals("folder" + File.separator + "1.jpg", filePath);
    }

    @Test
    public void createFolderIfNotExist_createFolder() {
        File folder = new File(filesHelper.getFolderPath() + File.separator + "test_folder");
        if (folder.exists()) {
            folder.delete();
        }

        filesHelper.createFolderIfNotExist(folder.getAbsolutePath());

        assertTrue(folder.exists());
    }

    @Test
    public void getFileOutputStream() throws IOException {
        File folder = new File(filesHelper.getFolderPath());
        filesHelper.createFolderIfNotExist(folder.getAbsolutePath());
        File file = new File(folder.getAbsolutePath() + File.separator + "test");
        if (file.exists()) {
            file.delete();
        }

        assertNotNull(filesHelper.getFileOutputStream(file.getAbsolutePath()));
    }

    @Test
    public void deleteFileIfExist_deleteFile() throws IOException {
        File folder = new File(filesHelper.getFolderPath());
        filesHelper.createFolderIfNotExist(folder.getAbsolutePath());
        File file = new File(folder.getAbsolutePath() + File.separator + "test");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        filesHelper.deleteFileIfExist(file.getAbsolutePath());

        assertFalse(file.exists());
    }

    @Test
    public void getPublicDirectory_returnPictureFolder() {
        String path = filesHelper.getPublicDirectory();

        assertEquals("/storage/emulated/0/Pictures", path);
    }

    @Test
    public void saveBitmapToStream_createFile() throws IOException {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGBA_F16);

        filesHelper.saveBitmapToStream(bitmap,
                filesHelper.getFileOutputStream(filesHelper.getFolderPath() + File.separator + "test"));
    }
}