package com.mss.imagesearcher.repositories.files;

import com.mss.imagesearcher.domain.models.ImageInfo;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class FilesHelperTest {

    @Test
    public void getFilePath() {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(1);
        FilesHelper filesHelper = new FilesHelper();

        assertEquals("path" + File.separator + "1.jpg", filesHelper.getFilePath("path", imageInfo));
    }
}