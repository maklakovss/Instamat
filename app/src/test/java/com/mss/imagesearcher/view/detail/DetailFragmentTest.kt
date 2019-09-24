package com.mss.imagesearcher.view.detail

import android.Manifest
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.mss.imagesearcher.R
import com.mss.imagesearcher.di.RobolectricApp
import com.mss.imagesearcher.di.RobolectricComponent
import com.mss.imagesearcher.presenter.detail.DetailPresenter
import com.mss.imagesearcher.robolectric.ShadowSnackbar
import com.mss.imagesearcher.view.helpers.ImageLoader
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowApplication
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = RobolectricApp::class, shadows = [ShadowSnackbar::class])
class DetailFragmentTest {

    private var detailFragment: DetailFragment? = null

    @Inject
    var presenter: DetailPresenter? = null

    @Inject
    var imageLoader: ImageLoader? = null

    @Before
    fun setUp() {
        val intent = Intent(RuntimeEnvironment.systemContext, DetailFragment::class.java)
        intent.putExtra(DetailFragment.PARAMETER_POSITION_TAG, 1)
        detailFragment = Robolectric.buildActivity(DetailFragment::class.java, intent)
                .create()
                .start()
                .resume()
                .visible()
                .get()
        (RobolectricApp.appComponent as RobolectricComponent).inject(this)
    }

    @Test
    fun onCreate_callPresenterOnCreateWithArgument() {
        verify<DetailPresenter>(presenter).onCreate(1)
    }

    @Test
    fun onMenuSaveClick_HasPermission_callPresenterOnCSaveClick() {
        val application = ShadowApplication()
        application.grantPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val shadowActivity = Shadows.shadowOf(detailFragment)
        val imageView = detailFragment!!.findViewById<ImageView>(R.id.imageView)
        imageView.setImageDrawable(BitmapDrawable())

        shadowActivity.clickMenuItem(R.id.miSave)

        verify<DetailPresenter>(presenter).onSaveClick(eq(1), any<Bitmap>())
    }

    @Test
    fun onMenuShareClick_callPresenterOnShareClick() {
        val application = ShadowApplication()
        application.grantPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val shadowActivity = Shadows.shadowOf(detailFragment)
        val imageView = detailFragment!!.findViewById<ImageView>(R.id.imageView)
        imageView.setImageDrawable(BitmapDrawable())

        shadowActivity.clickMenuItem(R.id.miShare)

        verify<DetailPresenter>(presenter).onShareClick(eq(1), any<Bitmap>())
    }

    @Test
    fun onShowSuccessSaveMessage_showSnackbar() {
        ShadowSnackbar.reset()

        detailFragment!!.showSuccessSaveMessage()

        assertEquals(ShadowSnackbar.textOfLatestSnackbar, detailFragment!!.getString(R.string.image_saved_message))
    }

    @Test
    fun onShowFailSaveMessage_showSnackbar() {
        ShadowSnackbar.reset()

        detailFragment!!.showFailedSaveMessage()

        assertEquals(ShadowSnackbar.textOfLatestSnackbar, detailFragment!!.getString(R.string.image_failed_message))
    }

    @Test
    fun shareImage_startIntentChooserImageWithPath() {
        detailFragment!!.shareImage("path")

        val intent = Shadows.shadowOf(detailFragment).nextStartedActivity
        assertEquals(Intent.ACTION_CHOOSER, intent.action)
        val intent1 = intent.extras!!.get(Intent.EXTRA_INTENT) as Intent?
        assertEquals(Intent.ACTION_SEND, intent1!!.action)
        assertEquals("image/jpeg", intent1.type)
        assertEquals("path", intent1.extras!!.get(Intent.EXTRA_STREAM)!!.toString())
    }

    @Test
    fun showProgress_Hide_pbDetailHide() {
        val progressBar = detailFragment!!.findViewById<ProgressBar>(R.id.pbDetail)

        detailFragment!!.showProgress(false)

        assertEquals(View.GONE.toLong(), progressBar.visibility.toLong())
    }

    @Test
    fun showProgress_Show_pbDetailShow() {
        val progressBar = detailFragment!!.findViewById<ProgressBar>(R.id.pbDetail)

        detailFragment!!.showProgress(true)

        assertEquals(View.VISIBLE.toLong(), progressBar.visibility.toLong())
    }

    @Test
    fun showImage_SuccessLoad_callPresenterOnImageLoaded() {

        doAnswer { invocation ->
            (invocation.arguments[3] as Runnable).run()
            null
        }.`when`<ImageLoader>(imageLoader).load(any<Context>(), any(), any(), any(), any())

        detailFragment!!.startLoadImage("path")

        verify<DetailPresenter>(presenter).onImageLoaded()
    }

    @Test
    fun showImage_FailLoad_callPresenterOnImageLoaded() {

        doAnswer { invocation ->
            (invocation.arguments[4] as Runnable).run()
            null
        }.`when`<ImageLoader>(imageLoader).load(any<Context>(), any(), any(), any(), any())

        detailFragment!!.startLoadImage("path")

        verify<DetailPresenter>(presenter).onImageLoadFailed()
    }

    @Test
    fun onMenuSaveClick_NoPermission_noCallPresenterOnSaveClick() {
        val shadowActivity = Shadows.shadowOf(detailFragment)

        shadowActivity.clickMenuItem(R.id.miSave)

        verify<DetailPresenter>(presenter, times(0)).onSaveClick(eq(1), any<Bitmap>())
    }

    @Test
    fun onMenuShareClick_NoPermission_noCallPresenterOnShareClick() {
        val shadowActivity = Shadows.shadowOf(detailFragment)

        shadowActivity.clickMenuItem(R.id.miSave)

        verify<DetailPresenter>(presenter, times(0)).onShareClick(eq(1), any<Bitmap>())
    }
}