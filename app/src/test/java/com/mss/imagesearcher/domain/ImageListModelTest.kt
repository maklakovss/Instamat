package com.mss.imagesearcher.domain

import android.graphics.Bitmap
import com.mss.imagesearcher.domain.models.ImageInfo
import com.mss.imagesearcher.domain.repositories.FilesRepository
import com.mss.imagesearcher.domain.repositories.ImagesNetRepository
import io.reactivex.Maybe
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ImageListModelTest {

    private var model: ImageListModel? = null
    private var images: MutableList<ImageInfo>? = null

    @Mock
    private val imagesNetRepository: ImagesNetRepository? = null
    @Mock
    private val filesRepository: FilesRepository? = null

    @Before
    fun setUp() {
        model = ImageListModel(imagesNetRepository!!, filesRepository!!)
    }

    private fun initImageInfoList() {
        images = ArrayList()
        for (i in 0..49) {
            val imageInfo = ImageInfo()
            imageInfo.id = i.toLong()
            imageInfo.previewURL = "https:\\\\previewurl$i"
            imageInfo.largeImageURL = "https:\\\\largeimagewurl$i"
            images!!.add(imageInfo)
        }
    }

    @Test
    fun getImagesFromNetwork_callNetRepositoryAndSaveResult() {
        initImageInfoList()
        `when`(imagesNetRepository!!.findImages(anyString(), anyInt())).thenReturn(Maybe.just(images!!))

        assertEquals(images!!.size.toLong(), model!!.getImagesFromNetwork("one", 1).blockingGet().size.toLong())

        verify(imagesNetRepository).findImages("one", 1)
        assertEquals(images!!.size.toLong(), model!!.images.size.toLong())

        model!!.clearImages()
        assertEquals(0, model!!.images.size.toLong())
    }

    @Test
    @Throws(IOException::class)
    fun saveBitmap() {
        initImageInfoList()
        `when`(filesRepository!!.saveBitmap(any(), any<Bitmap>())).thenReturn("path")

        assertEquals("path", model!!.saveBitmap(images!![0], null!!))

        verify(filesRepository).saveBitmap(images!![0], null!!)
    }

    companion object {

        @BeforeClass
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }
}