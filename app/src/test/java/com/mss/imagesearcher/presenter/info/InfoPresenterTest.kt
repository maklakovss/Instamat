package com.mss.imagesearcher.presenter.info

import com.mss.imagesearcher.domain.ImageListModel
import com.mss.imagesearcher.domain.models.ImageInfo
import com.mss.imagesearcher.view.info.InfoView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class InfoPresenterTest {

    @Mock
    private val infoView: InfoView? = null

    @Mock
    private val model: ImageListModel? = null

    private var infoPresenter: InfoPresenter? = null
    private var imageInfoList: MutableList<ImageInfo>? = null


    @Before
    @Throws(Exception::class)
    fun setUp() {
        infoPresenter = spy(InfoPresenter(model))
        infoPresenter!!.attachView(infoView)
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
    fun onCreate_InitAdMobAndShowInfo() {
        initImageInfoList()

        infoPresenter!!.onCreate(1)

        verify<InfoView>(infoView).initAdMob()
        verify<InfoView>(infoView).showInfo(imageInfoList!![1])
    }

    @Test
    fun onAdLoaded() {
        initImageInfoList()

        infoPresenter!!.onAdLoaded()

        verify<InfoView>(infoView).showFullScreenAd()
    }
}