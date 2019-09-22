package com.mss.imagesearcher.view.imagelist

import android.view.KeyEvent
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputEditText
import com.mss.imagesearcher.R
import com.mss.imagesearcher.di.RobolectricApp
import com.mss.imagesearcher.di.RobolectricComponent
import com.mss.imagesearcher.domain.models.ImageInfo
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter
import com.mss.imagesearcher.robolectric.ShadowSnackbar
import com.mss.imagesearcher.view.detail.DetailActivity
import com.mss.imagesearcher.view.helpers.ImageLoader
import com.mss.imagesearcher.view.main.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowApplication
import java.util.*
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = RobolectricApp::class, shadows = [ShadowSnackbar::class])
class MainActivityTest {

    private var mainActivity: MainActivity? = null
    private var imageInfoList: MutableList<ImageInfo>? = null

    @Inject
    var presenter: ImageListPresenter? = null

    @Inject
    var imageLoader: ImageLoader? = null

    @Before
    fun setUp() {
        mainActivity = Robolectric.buildActivity(MainActivity::class.java)
                .create()
                .start()
                .resume()
                .visible()
                .get()
        (RobolectricApp.appComponent as RobolectricComponent).inject(this)
    }

    @Test
    fun showProgress_Hide_pbDetailHide() {
        val progressBar = mainActivity!!.findViewById<ProgressBar>(R.id.pbList)

        mainActivity!!.showProgress(false)

        assertEquals(View.INVISIBLE.toLong(), progressBar.visibility.toLong())
    }

    @Test
    fun showProgress_Show_pbDetailShow() {
        val progressBar = mainActivity!!.findViewById<ProgressBar>(R.id.pbList)

        mainActivity!!.showProgress(true)

        assertEquals(View.VISIBLE.toLong(), progressBar.visibility.toLong())
    }

    @Test
    fun stopRefreshing_pbDetailShow() {
        val swipeRefreshLayout = mainActivity!!.findViewById<SwipeRefreshLayout>(R.id.srlImages)
        swipeRefreshLayout.isRefreshing = true

        mainActivity!!.stopRefreshing()

        assertFalse(swipeRefreshLayout.isRefreshing)
    }

    @Test
    fun refreshImageList_callPresenterGetRvPresenter() {
        initImageInfoList()
        val rvPresenter = mock(ImageListPresenter.RvPresenter::class.java)
        `when`<ImageListPresenter.RvPresenter>(presenter!!.rvPresenter).thenReturn(rvPresenter)
        `when`(rvPresenter.itemCount).thenReturn(imageInfoList!!.size)

        mainActivity!!.refreshImageList()
        mainActivity!!.refreshImageList()

        verify<ImageListPresenter>(presenter).rvPresenter
    }

    @Test
    fun showNotFoundMessage_showSnackbar() {
        ShadowSnackbar.reset()

        mainActivity!!.showNotFoundMessage()

        assertEquals(ShadowSnackbar.textOfLatestSnackbar, mainActivity!!.getString(R.string.not_found_message))
    }

    @Test
    fun openDetailActivity_startDetailActivityWithParam() {
        mainActivity!!.openDetailActivity(1)

        val intent = Shadows.shadowOf(mainActivity).nextStartedActivity
        assertEquals("com.mss.imagesearcher.view.detail.DetailActivity", intent.component!!.className)
        assertEquals(1, intent.getIntExtra(DetailActivity.PARAMETER_POSITION_TAG, 0).toLong())
    }

    @Test
    fun onRequestPermissionsResult_NoPermission_FinishActivity() {
        mainActivity!!.onRequestPermissionsResult(MainActivity.PERMISSION_REQUEST_CODE, arrayOfNulls(2), IntArray(2))
        assertTrue(Shadows.shadowOf(mainActivity).isFinishing)
    }

    @Test
    fun onRequestPermissionsResult_HasPermission_NoFinishActivity() {
        val application = ShadowApplication()
        for (p in MainActivity.NETWORK_PERMISSIONS) {
            application.grantPermissions(p)
        }

        mainActivity!!.onRequestPermissionsResult(MainActivity.PERMISSION_REQUEST_CODE, arrayOfNulls(2), IntArray(2))

        assertTrue(!Shadows.shadowOf(mainActivity).isFinishing)
    }

    @Test
    fun onItemClick_callPresenter() {
        initImageInfoList()
        val rvPresenter = mock(ImageListPresenter.RvPresenter::class.java)
        `when`<RvPresenter>(presenter!!.rvPresenter).thenReturn(rvPresenter)
        `when`(rvPresenter.itemCount).thenReturn(imageInfoList!!.size)
        val recyclerView = mainActivity!!.findViewById<RecyclerView>(R.id.rvImages)
        recyclerView.measure(0, 0)
        recyclerView.layout(0, 0, 100, 800)
        mainActivity!!.refreshImageList()
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(1) as ImageListAdapter.ViewHolder?

        viewHolder!!.ivItem!!.performClick()

        verify<ImageListPresenter>(presenter).onItemClick(1)
    }

    @Test
    fun holderSetImage_SuccessLoad_callPresenterOnImageLoaded() {
        initImageInfoList()
        val rvPresenter = mock(ImageListPresenter.RvPresenter::class.java)
        `when`<RvPresenter>(presenter!!.rvPresenter).thenReturn(rvPresenter)
        `when`(rvPresenter.itemCount).thenReturn(imageInfoList!!.size)
        val recyclerView = mainActivity!!.findViewById<RecyclerView>(R.id.rvImages)
        recyclerView.measure(0, 0)
        recyclerView.layout(0, 0, 100, 200)
        mainActivity!!.refreshImageList()
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(1) as ImageListAdapter.ViewHolder?
        doAnswer { answer ->
            (answer.getArgument<Any>(3) as Runnable).run()
            null
        }.`when`<ImageLoader>(imageLoader).load(any<Context>(), any(), any<ImageView>(), any(), any())

        viewHolder!!.setImage("path")

        assertEquals(1, viewHolder.pos.toLong())
        verify(rvPresenter).onImageLoaded(any())
    }

    @Test
    fun holderSetImage_FailLoad_callPresenterOnImageLoaded() {
        initImageInfoList()
        val rvPresenter = mock(ImageListPresenter.RvPresenter::class.java)
        `when`<RvPresenter>(presenter!!.rvPresenter).thenReturn(rvPresenter)
        `when`(rvPresenter.itemCount).thenReturn(imageInfoList!!.size)
        val recyclerView = mainActivity!!.findViewById<RecyclerView>(R.id.rvImages)
        recyclerView.measure(0, 0)
        recyclerView.layout(0, 0, 100, 200)
        mainActivity!!.refreshImageList()
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(1) as ImageListAdapter.ViewHolder?
        doAnswer { answer ->
            (answer.getArgument<Any>(4) as Runnable).run()
            null
        }.`when`<ImageLoader>(imageLoader).load(any<Context>(), any(), any<ImageView>(), any(), any())

        viewHolder!!.setImage("path")

        verify<RvPresenter>(rvPresenter).onImageLoadFailed(any<IImageListViewHolder>())
    }

    @Test
    fun holderShowProgress_True_ProgressBarVisible() {
        initImageInfoList()
        val rvPresenter = mock(ImageListPresenter.RvPresenter::class.java)
        `when`<RvPresenter>(presenter!!.rvPresenter).thenReturn(rvPresenter)
        `when`(rvPresenter.itemCount).thenReturn(imageInfoList!!.size)
        val recyclerView = mainActivity!!.findViewById<RecyclerView>(R.id.rvImages)
        recyclerView.measure(0, 0)
        recyclerView.layout(0, 0, 100, 200)
        mainActivity!!.refreshImageList()
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(1) as ImageListAdapter.ViewHolder?

        viewHolder!!.showProgress(true)

        assertEquals(View.VISIBLE.toLong(), viewHolder.pbItem!!.visibility.toLong())
    }

    @Test
    fun holderShowProgress_False_ProgressBarVisible() {
        initImageInfoList()
        val rvPresenter = mock(ImageListPresenter.RvPresenter::class.java)
        `when`<RvPresenter>(presenter!!.rvPresenter).thenReturn(rvPresenter)
        `when`(rvPresenter.itemCount).thenReturn(imageInfoList!!.size)
        val recyclerView = mainActivity!!.findViewById<RecyclerView>(R.id.rvImages)
        recyclerView.measure(0, 0)
        recyclerView.layout(0, 0, 100, 200)
        mainActivity!!.refreshImageList()
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(1) as ImageListAdapter.ViewHolder?

        viewHolder!!.showProgress(false)

        assertEquals(View.GONE.toLong(), viewHolder.pbItem!!.visibility.toLong())
    }

    @Test
    fun onAction_callPresenterOnSearchClick() {
        val etSearch = mainActivity!!.findViewById<TextInputEditText>(R.id.etSearch)
        etSearch.setText("one")
        etSearch.onKeyDown(KeyEvent.KEYCODE_ENTER, KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))

        verify<ImageListPresenter>(presenter).onSearchClick("one")
    }

    private fun initImageInfoList() {
        imageInfoList = ArrayList()
        for (i in 0..49) {
            val imageInfo = ImageInfo()
            imageInfo.id = i
            imageInfo.previewURL = "https:\\\\previewurl$i"
            imageInfo.largeImageURL = "https:\\\\largeimagewurl$i"
            imageInfoList!!.add(imageInfo)
        }
    }
}