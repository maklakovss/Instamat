package com.mss.imagesearcher.model.repositories.files

import android.graphics.Bitmap
import com.mss.imagesearcher.model.entity.ImageInfo
import com.mss.imagesearcher.model.repositories.FilesRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.io.OutputStream

@RunWith(MockitoJUnitRunner::class)
class FilesRepositoryImplTest {

    private var filesRepository: FilesRepository? = null

    @Mock
    private val filesHelper: FilesHelper? = null

    @Before
    fun setUp() {
        filesRepository = spy(FilesRepositoryImpl(filesHelper!!))
    }

    @Test
    @Throws(IOException::class)
    fun saveBitmap() {
        val imageInfo = ImageInfo()
        imageInfo.id = 1
        `when`(filesHelper!!.folderPath).thenReturn("path")
        `when`(filesHelper.getFilePath("path", imageInfo)).thenReturn("path\\1")

        filesRepository!!.saveBitmap(imageInfo, null!!)

        verify(filesHelper).folderPath
        verify(filesHelper).createFolderIfNotExist("path")
        verify(filesHelper).getFilePath("path", imageInfo)
        verify(filesHelper).deleteFileIfExist("path\\1")
        verify(filesHelper).saveBitmapToStream(any<Bitmap>(), any<OutputStream>())
    }
}