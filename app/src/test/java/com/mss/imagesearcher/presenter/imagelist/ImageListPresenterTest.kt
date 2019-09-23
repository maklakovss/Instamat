package com.mss.imagesearcher.presenter.imagelist

import com.mss.imagesearcher.domain.ImageListModel
import com.mss.imagesearcher.domain.models.ImageInfo
import com.mss.imagesearcher.view.imagelist.IImageListViewHolder
import com.mss.imagesearcher.view.imagelist.ListFragmentView
import io.reactivex.Maybe
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ImageListPresenterTest {

    @Mock
    private val listFragmentView: ListFragmentView? = null

    @Mock
    private val model: ImageListModel? = null

    private var imageListPresenter: ImageListPresenter? = null
    private var imageInfoList: MutableList<ImageInfo>? = null

    @Before
    fun setUp() {
        imageListPresenter = spy(ImageListPresenter(model!!))
        imageListPresenter!!.attachView(listFragmentView)
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
    fun onItemClick_openDetailActivity() {
        imageListPresenter!!.onItemClick(1)
        verify<ListFragmentView>(listFragmentView).openDetailActivity(1)
    }

    @Test
    fun getRvPresenter_returnNotNull() {
        assertNotNull(imageListPresenter!!.rvPresenter)
    }

    @Test
    fun onSearchClick_showResultFromNetwork() {
        initImageInfoList()
        `when`(model!!.getImagesFromNetwork(anyString(), eq(1))).thenReturn(Maybe.just(imageInfoList!!))

        imageListPresenter!!.onSearchClick("one")

        verify<ListFragmentView>(listFragmentView).showProgress(true)
        verify(model).getImagesFromNetwork("one", 1)
        verify<ListFragmentView>(listFragmentView, times(2)).refreshImageList()
        verify<ListFragmentView>(listFragmentView).showProgress(false)
    }

    @Test
    fun onSearchClick_queryReturnNetworkException_showNotFoundMessage() {
        `when`(model!!.images).thenReturn(ArrayList())
        `when`(model.getImagesFromNetwork(anyString(), eq(1))).thenReturn(Maybe.error(Exception()))

        imageListPresenter!!.onSearchClick("one")

        verify<ListFragmentView>(listFragmentView).showProgress(true)
        verify(model).getImagesFromNetwork("one", 1)
        verify<ListFragmentView>(listFragmentView, times(1)).refreshImageList()
        verify<ListFragmentView>(listFragmentView).showProgress(false)
        verify<ListFragmentView>(listFragmentView).showNotFoundMessage()
    }

    @Test
    fun onNeedNextPage_showResultFromNetwork() {
        initImageInfoList()
        `when`(model!!.getImagesFromNetwork(anyString(), eq(1))).thenReturn(Maybe.just(imageInfoList!!))

        imageListPresenter!!.onNeedNextPage()

        verify<ListFragmentView>(listFragmentView).showProgress(true)
        verify(model, times(0)).getImagesFromNetwork("one", 1)
        verify<ListFragmentView>(listFragmentView).refreshImageList()
        verify<ListFragmentView>(listFragmentView).showProgress(false)
    }

    @Test
    fun onRefresh_showResultFromNetwork() {
        initImageInfoList()
        `when`(model!!.getImagesFromNetwork(anyString(), eq(1))).thenReturn(Maybe.just(imageInfoList!!))

        imageListPresenter!!.onRefresh("one")

        verify<ListFragmentView>(listFragmentView).showProgress(true)
        verify(model).getImagesFromNetwork("one", 1)
        verify<ListFragmentView>(listFragmentView, times(2)).refreshImageList()
        verify<ListFragmentView>(listFragmentView).showProgress(false)
    }

    @Test
    fun onCreate_InitAdMob() {
        imageListPresenter!!.onCreate()

        verify<ListFragmentView>(listFragmentView).initAdMob()
    }

    @Test
    fun onPrivacyPolicyClick_ShowPrivacyPolicy() {
        imageListPresenter!!.onPrivacyPolicyClick()

        verify<ListFragmentView>(listFragmentView).showPrivacyPolicy()
    }

    @Test
    fun RvPresenterBindView_showProgressSetImage() {
        initImageInfoList()
        val holder = mock(IImageListViewHolder::class.java)
        `when`(holder.pos).thenReturn(1)

        imageListPresenter!!.rvPresenter.bindView(holder)

        verify(holder).showProgress(true)
        verify(holder).setImage(imageInfoList!![1].webFormatURL!!)
    }

    @Test
    fun RvPresenterGetItemCount_returnImagesCount() {
        initImageInfoList()

        assertEquals(imageInfoList!!.size.toLong(), imageListPresenter!!.rvPresenter.itemCount.toLong())
    }

    @Test
    fun RvPresenterOnImageLoaded_stopProgress() {
        val holder = mock(IImageListViewHolder::class.java)

        imageListPresenter!!.rvPresenter.onImageLoaded(holder)

        verify(holder).showProgress(false)
    }

    @Test
    fun RvPresenterOnImageLoadFailed_stopProgress() {
        val holder = mock(IImageListViewHolder::class.java)

        imageListPresenter!!.rvPresenter.onImageLoadFailed(holder)

        verify(holder).showProgress(false)
    }

    companion object {

        @BeforeClass
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }
}