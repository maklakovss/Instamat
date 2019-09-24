package com.mss.imagesearcher.model.repositories.files

import com.mss.imagesearcher.model.entity.ImageInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class FilesHelperTest {

    @Test
    fun getFilePath() {
        val imageInfo = ImageInfo()
        imageInfo.id = 1
        val filesHelper = FilesHelper()

        assertEquals("path" + File.separator + "1.jpg", filesHelper.getFilePath("path", imageInfo))
    }
}