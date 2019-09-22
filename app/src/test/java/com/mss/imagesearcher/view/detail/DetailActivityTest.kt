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
class DetailActivityTest {

    private var detailActivity: DetailActivity? = null

    @Inject
    var presenter: DetailPresenter? = null

    @Inject
    var imageLoader: ImageLoader? = null

    @Before
    fun setUp() {
        val intent = Intent(RuntimeEnvironment.systemContext, DetailActivity::class.java)
        intent.putExtra(DetailActivity.PARAMETER_POSITION_TAG, 1)
        detailActivity = Robolectric.buildActivity(DetailActivity::class.java, intent)
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
        val shadowActivity = Shadows.shadowOf(detailActivity)
        val imageView = detailActivity!!.findViewById<ImageView>(R.id.imageView)
        imageView.setImageDrawable(BitmapDrawable())

        shadowActivity.clickMenuItem(R.id.miSave)

        verify<DetailPresenter>(presenter).onSaveClick(eq(1), any<Bitmap>())
    }

    @Test
    fun onMenuShareClick_callPresenterOnShareClick() {
        val application = ShadowApplication()
        application.grantPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val shadowActivity = Shadows.shadowOf(detailActivity)
        val imageView = detailActivity!!.findViewById<ImageView>(R.id.imageView)
        imageView.setImageDrawable(BitmapDrawable())

        shadowActivity.clickMenuItem(R.id.miShare)

        verify<DetailPresenter>(presenter).onShareClick(eq(1), any<Bitmap>())
    }

    @Test
    fun onShowSuccessSaveMessage_showSnackbar() {
        ShadowSnackbar.reset()

        detailActivity!!.showSuccessSaveMessage()

        assertEquals(ShadowSnackbar.textOfLatestSnackbar, detailActivity!!.getString(R.string.image_saved_message))
    }

    @Test
    fun onShowFailSaveMessage_showSnackbar() {
        ShadowSnackbar.reset()

        detailActivity!!.showFailedSaveMessage()

        assertEquals(ShadowSnackbar.textOfLatestSnackbar, detailActivity!!.getString(R.string.image_failed_message))
    }

    @Test
    fun shareImage_startIntentChooserImageWithPath() {
        detailActivity!!.shareImage("path")

        val intent = Shadows.shadowOf(detailActivity).nextStartedActivity
        assertEquals(Intent.ACTION_CHOOSER, intent.action)
        val intent1 = intent.extras!!.get(Intent.EXTRA_INTENT) as Intent?
        assertEquals(Intent.ACTION_SEND, intent1!!.action)
        assertEquals("image/jpeg", intent1.type)
        assertEquals("path", intent1.extras!!.get(Intent.EXTRA_STREAM)!!.toString())
    }

    @Test
    fun showProgress_Hide_pbDetailHide() {
        val progressBar = detailActivity!!.findViewById<ProgressBar>(R.id.pbDetail)

        detailActivity!!.showProgress(false)

        assertEquals(View.GONE.toLong(), progressBar.visibility.toLong())
    }

    @Test
    fun showProgress_Show_pbDetailShow() {
        val progressBar = detailActivity!!.findViewById<ProgressBar>(R.id.pbDetail)

        detailActivity!!.showProgress(true)

        assertEquals(View.VISIBLE.toLong(), progressBar.visibility.toLong())
    }

    @Test
    fun showImage_SuccessLoad_callPresenterOnImageLoaded() {

        doAnswer { invocation ->
            (invocation.arguments[3] as Runnable).run()
            null
        }.`when`<ImageLoader>(imageLoader).load(any<Context>(), any(), any(), any(), any())

        detailActivity!!.startLoadImage("path")

        verify<DetailPresenter>(presenter).onImageLoaded()
    }

    @Test
    fun showImage_FailLoad_callPresenterOnImageLoaded() {

        doAnswer { invocation ->
            (invocation.arguments[4] as Runnable).run()
            null
        }.`when`<ImageLoader>(imageLoader).load(any<Context>(), any(), any(), any(), any())

        detailActivity!!.startLoadImage("path")

        verify<DetailPresenter>(presenter).onImageLoadFailed()
    }

    @Test
    fun onMenuSaveClick_NoPermission_noCallPresenterOnSaveClick() {
        val shadowActivity = Shadows.shadowOf(detailActivity)

        shadowActivity.clickMenuItem(R.id.miSave)

        verify<DetailPresenter>(presenter, times(0)).onSaveClick(eq(1), any<Bitmap>())
    }

    @Test
    fun onMenuShareClick_NoPermission_noCallPresenterOnShareClick() {
        val shadowActivity = Shadows.shadowOf(detailActivity)

        shadowActivity.clickMenuItem(R.id.miSave)

        verify<DetailPresenter>(presenter, times(0)).onShareClick(eq(1), any<Bitmap>())
    }
}