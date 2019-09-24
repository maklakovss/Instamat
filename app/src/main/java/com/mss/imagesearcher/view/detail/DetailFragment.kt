package com.mss.imagesearcher.view.detail

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.mss.imagesearcher.App
import com.mss.imagesearcher.R
import com.mss.imagesearcher.presenter.detail.DetailPresenter
import com.mss.imagesearcher.view.helpers.ImageLoader
import com.mss.imagesearcher.view.info.InfoActivity
import kotlinx.android.synthetic.main.activity_detail.*
import timber.log.Timber
import javax.inject.Inject

class DetailFragment : MvpAppCompatFragment(), DetailView {

    companion object {
        const val PARAMETER_POSITION_TAG = "PARAMETER_POSITION_TAG"
    }

    private var position = 0

    @Inject
    @InjectPresenter
    lateinit var presenter: DetailPresenter

    @Inject
    lateinit var imageLoader: ImageLoader

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onCreate")
        presenter.onCreate(position)
    }

    @ProvidePresenter
    fun providePresenter(): DetailPresenter {
        return presenter
    }

    override fun showInfo() {
        Timber.d("openDetailActivity")
        val intent = Intent(context, InfoActivity::class.java)
        intent.putExtra(InfoActivity.PARAMETER_POSITION_TAG, position)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miInfo -> {
                presenter.onInfoClick()
                return true
            }
            R.id.miSave -> {
                saveImage()
                return true
            }
            R.id.miShare -> {
                shareImage()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun startLoadImage(imageURL: String) {
        Timber.d("startLoadImage %s", imageURL)
        context?.let {
            imageLoader.load(context!!,
                    imageURL,
                    imageView!!,
                    { presenter.onImageLoaded() },
                    { presenter.onImageLoadFailed() })
        }
    }

    override fun showProgress(visible: Boolean) {
        Timber.d("showProgress %b", visible)
        if (visible) {
            pbDetail!!.visibility = View.VISIBLE
        } else {
            pbDetail!!.visibility = View.GONE
        }
    }

    override fun showSuccessSaveMessage() {
        Timber.d("showSuccessSaveMessage")
        Snackbar.make(imageView!!, R.string.image_saved_message, Snackbar.LENGTH_SHORT).show()
    }

    override fun showFailedSaveMessage() {
        Timber.d("showFailedSaveMessage")
        Snackbar.make(imageView!!, R.string.image_failed_message, Snackbar.LENGTH_SHORT).show()
    }

    override fun shareImage(path: String) {
        Timber.d("shareImage")
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path))
        startActivity(Intent.createChooser(intent, "Share Image"))
    }

    override fun showImage(visible: Boolean) {
        Timber.d("showImage %b", visible)
        if (visible) {
            imageView!!.visibility = View.VISIBLE
        } else {
            imageView!!.visibility = View.INVISIBLE
        }
    }

    private fun saveImage() {
        Timber.d("saveImage")
        val drawable = imageView!!.drawable as BitmapDrawable
        presenter.onSaveClick(position, drawable.bitmap)
    }

    private fun shareImage() {
        Timber.d("shareImage")
        val drawable = imageView!!.drawable as BitmapDrawable
        presenter.onShareClick(position, drawable.bitmap)
    }
}
