package com.mss.imagesearcher.presenter.detail

import android.graphics.Bitmap
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.model.entity.ImageInfo
import com.mss.imagesearcher.view.detail.DetailView
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DetailPresenterTest {

    @Mock
    private val detailView: DetailView? = null

    @Mock
    private val model: ImageListModel? = null

    private var detailPresenter: DetailPresenter? = null
    private var imageInfoList: MutableList<ImageInfo>? = null

    @Before
    fun setUp() {
        detailPresenter = spy(DetailPresenter(model!!))
        detailPresenter!!.attachView(detailView)
    }

    private fun initImageInfoList() {
        imageInfoList = ArrayList()
        for (i in 0..49) {
            val imageInfo = ImageInfo()
            imageInfo.id = i.toLong()
            imageInfo.webFormatURL = "https:\\\\previewurl$i"
            imageInfo.largeImageURL = "https:\\\\largeimagewurl$i"
            imageInfoList!!.add(imageInfo)
        }

        `when`(model!!.images).thenReturn(imageInfoList)
    }

    @Test
    fun onCreate_startShowImageWithStartProgress() {
        initImageInfoList()

        detailPresenter!!.onCreate(1)

        verify<DetailView>(detailView).showProgress(true)
        verify<DetailView>(detailView).startLoadImage(imageInfoList!![1].largeImageURL!!)
    }

    @Test
    fun onImageLoaded_stopProgress() {
        detailPresenter!!.onImageLoaded()

        verify<DetailView>(detailView).showProgress(false)
    }

    @Test
    fun onImageLoadFailed_stopProgress() {
        detailPresenter!!.onImageLoadFailed()

        verify<DetailView>(detailView).showProgress(false)
    }

    @Test
    @Throws(IOException::class)
    fun onSaveClick_saveBitmapShowMessage() {
        initImageInfoList()

        detailPresenter!!.onSaveClick(1, null!!)

        verify<ImageListModel>(model).saveBitmap(imageInfoList!![1], null!!)
        verify<DetailView>(detailView).showSuccessSaveMessage()
    }

    @Test
    @Throws(IOException::class)
    fun onSaveClick_throwIOException_saveBitmapShowFailMessage() {
        initImageInfoList()
        `when`(model!!.saveBitmap(any(), any<Bitmap>())).thenThrow(IOException())

        detailPresenter!!.onSaveClick(1, null!!)

        verify(model).saveBitmap(imageInfoList!![1], null!!)
        verify<DetailView>(detailView).showFailedSaveMessage()
    }

    @Test
    @Throws(IOException::class)
    fun onSaveClick_throwFileNotFoundException_saveBitmapShowFailMessage() {
        initImageInfoList()
        `when`(model!!.saveBitmap(any(), any<Bitmap>())).thenThrow(FileNotFoundException())

        detailPresenter!!.onSaveClick(1, null!!)

        verify(model).saveBitmap(imageInfoList!![1], null!!)
        verify<DetailView>(detailView).showFailedSaveMessage()
    }

    @Test
    @Throws(IOException::class)
    fun onShareClick_saveBitmapShareImage() {
        initImageInfoList()
        `when`(model!!.saveBitmap(any(), any<Bitmap>())).thenReturn("path")

        detailPresenter!!.onShareClick(1, null!!)

        verify(model).saveBitmap(imageInfoList!![1], null!!)
        verify<DetailView>(detailView).shareImage("path")
    }

    @Test
    @Throws(IOException::class)
    fun onShareClick_throwIOException_saveBitmapShowFailMessage() {
        initImageInfoList()
        `when`(model!!.saveBitmap(any(), any<Bitmap>())).thenThrow(IOException())

        detailPresenter!!.onShareClick(1, null!!)

        verify(model).saveBitmap(imageInfoList!![1], null!!)
        verify<DetailView>(detailView).showFailedSaveMessage()
    }

    @Test
    @Throws(IOException::class)
    fun onShareClick_throwFileNotFoundException_saveBitmapShowFailMessage() {
        initImageInfoList()
        `when`(model!!.saveBitmap(any(), any<Bitmap>())).thenThrow(FileNotFoundException())

        detailPresenter!!.onShareClick(1, null!!)

        verify(model).saveBitmap(imageInfoList!![1], null!!)
        verify<DetailView>(detailView).showFailedSaveMessage()
    }

    @Test
    @Throws(IOException::class)
    fun onAdLoaded__ShowFullScreenAd() {
        detailPresenter!!.onAdLoaded()

        verify<DetailView>(detailView).showFullScreenAd()
    }

    @Test
    @Throws(IOException::class)
    fun onInfoClick_ShowInfo() {
        detailPresenter!!.onInfoClick()

        verify<DetailView>(detailView).showInfo()
    }

    companion object {

        @BeforeClass
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { __ -> Schedulers.trampoline() }
        }
    }
}