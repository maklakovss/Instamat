package com.mss.imagesearcher.model.repositories.network

import com.mss.imagesearcher.model.repositories.ImagesNetRepository
import com.mss.imagesearcher.model.repositories.network.entity.ImageInfoNet
import com.mss.imagesearcher.model.repositories.network.entity.ImagesResponse
import io.reactivex.Maybe
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ImagesNetRepositoryImplTest {

    private var repository: ImagesNetRepository? = null

    @Mock
    private val pixabayAPI: PixabayAPI? = null

    private val imagesResponse: ImagesResponse
        get() {
            val imagesResponse = ImagesResponse()
            val imageInfoNet = ImageInfoNet()
            val imagesNet = ArrayList<ImageInfoNet>()
            imagesNet.add(imageInfoNet)
            imagesResponse.hits = imagesNet
            return imagesResponse
        }

    @Before
    fun setUp() {
        repository = ImagesNetRepositoryImpl(pixabayAPI!!)
    }

    @Test
    fun findImages_callAPIAndReturnsMappedImages() {
        val imagesResponse = imagesResponse
        `when`(pixabayAPI!!.findImages(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(Maybe.just(imagesResponse))

        assertEquals(imagesResponse.hits!!.size.toLong(), repository!!.findImages("one", 1).blockingGet().size.toLong())

        verify(pixabayAPI).findImages(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt())
    }

    companion object {

        @BeforeClass
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }
}