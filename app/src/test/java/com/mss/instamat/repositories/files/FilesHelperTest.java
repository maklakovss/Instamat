package com.mss.instamat.repositories.files;

import com.mss.instamat.domain.models.ImageInfo;

import org.junit.Test;

import static org.junit.Assert.*;

public class FilesHelperTest {

    @Test
    public void getFilePath() {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(1);
        FilesHelper filesHelper = new FilesHelper();

        assertEquals("path\\1.jpg", filesHelper.getFilePath("path", imageInfo));
    }
}