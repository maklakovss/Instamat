package com.mss.imagesearcher.view.info

import android.content.Intent
import android.widget.TextView
import com.mss.imagesearcher.R
import com.mss.imagesearcher.di.RobolectricApp
import com.mss.imagesearcher.di.RobolectricComponent
import com.mss.imagesearcher.model.entity.ImageInfo
import com.mss.imagesearcher.presenter.info.InfoPresenter
import com.mss.imagesearcher.robolectric.ShadowSnackbar
import com.mss.imagesearcher.view.detail.DetailActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = RobolectricApp::class, shadows = [ShadowSnackbar::class])
class InfoActivityTest {

    @Inject
    var presenter: InfoPresenter? = null

    private var infoActivity: InfoActivity? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val intent = Intent(RuntimeEnvironment.systemContext, InfoActivity::class.java)
        intent.putExtra(DetailActivity.PARAMETER_POSITION_TAG, 1)
        infoActivity = Robolectric.buildActivity(InfoActivity::class.java, intent)
                .create()
                .start()
                .resume()
                .visible()
                .get()
        (RobolectricApp.appComponent as RobolectricComponent).inject(this)
    }

    @Test
    fun onCreate_callPresenterOnCreateWithArgument() {
        verify<InfoPresenter>(presenter).onCreate(1)
    }

    @Test
    fun showInfo() {
        val imageInfo = ImageInfo()
        imageInfo.width = 1000
        imageInfo.height = 2000
        imageInfo.views = 1
        imageInfo.likes = 2
        imageInfo.comments = 3
        imageInfo.type = "photo"
        imageInfo.tags = "tags"
        imageInfo.pageUrl = "URL"

        val tvSize = infoActivity!!.findViewById<TextView>(R.id.tvSize)
        val tvViews = infoActivity!!.findViewById<TextView>(R.id.tvViews)
        val tvLikes = infoActivity!!.findViewById<TextView>(R.id.tvLikes)
        val tvComments = infoActivity!!.findViewById<TextView>(R.id.tvComments)
        val tvType = infoActivity!!.findViewById<TextView>(R.id.tvType)
        val tvTags = infoActivity!!.findViewById<TextView>(R.id.tvTags)
        val tvUrl = infoActivity!!.findViewById<TextView>(R.id.tvUrl)


        infoActivity!!.showInfo(imageInfo)


        assertEquals("2000 x 1000", tvSize.text)
        assertEquals("1", tvViews.text)
        assertEquals("2", tvLikes.text)
        assertEquals("3", tvComments.text)
        assertEquals("photo", tvType.text)
        assertEquals("tags", tvTags.text)
        assertEquals("URL", tvUrl.text)
    }
}