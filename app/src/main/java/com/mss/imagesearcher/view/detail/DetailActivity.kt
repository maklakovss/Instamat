package com.mss.imagesearcher.view.detail

import android.Manifest
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.material.snackbar.Snackbar
import com.jsibbold.zoomage.ZoomageView
import com.mss.imagesearcher.App
import com.mss.imagesearcher.R
import com.mss.imagesearcher.presenter.detail.DetailPresenter
import com.mss.imagesearcher.view.helpers.ImageLoader
import com.mss.imagesearcher.view.info.InfoActivity
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import javax.inject.Inject

class DetailActivity : MvpAppCompatActivity(), DetailView {

    companion object {
        const val PARAMETER_POSITION_TAG = "PARAMETER_POSITION_TAG"

        private const val PERMISSION_REQUEST_SAVE = 555
        private const val PERMISSION_REQUEST_SHARE = 888
        private val STORAGE_PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private var position = 0
    private var interstitialAd: InterstitialAd? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: DetailPresenter

    @Inject
    lateinit var imageLoader: ImageLoader

    @BindView(R.id.pbDetail)
    var pbDetail: ProgressBar? = null

    @BindView(R.id.imageView)
    var imageView: ZoomageView? = null

    @BindView(R.id.adView)
    var adView: AdView? = null

    @ProvidePresenter
    fun providePresenter(): DetailPresenter {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ButterKnife.bind(this)
        setTitle(R.string.detail_activity_title)

        getParameters()
        presenter.onCreate(position)
    }

    override fun initAdMob() {
        interstitialAd = InterstitialAd(applicationContext)
        interstitialAd!!.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                Timber.d("onAdClosed")
            }

            override fun onAdFailedToLoad(i: Int) {
                super.onAdFailedToLoad(i)
                Timber.d("onAdFailedToLoad %d", i)
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                Timber.d("onAdLoaded")
                presenter.onAdLoaded()
            }

            override fun onAdClicked() {
                super.onAdClicked()
                Timber.d("onAdClicked")
            }
        }
        interstitialAd!!.adUnitId = getString(R.string.banner_between_page_id)
        val adRequest = AdRequest.Builder().build()
        interstitialAd!!.loadAd(adRequest)

        adView!!.loadAd(AdRequest.Builder().build())
    }

    override fun showFullScreenAd() {
        if (interstitialAd != null) {
            if (interstitialAd!!.isLoaded) {
                interstitialAd!!.show()
            }
        }
    }

    override fun showInfo() {
        Timber.d("openDetailActivity")
        val intent = Intent(this, InfoActivity::class.java)
        intent.putExtra(InfoActivity.PARAMETER_POSITION_TAG, position)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (interstitialAd != null) {
            interstitialAd!!.adListener = null
            interstitialAd = null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miInfo -> {
                presenter.onInfoClick()
                return true
            }
            R.id.miSave -> {
                saveImageWithCheckPermission()
                return true
            }
            R.id.miShare -> {
                shareImageWithCheckPermission()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Timber.d("onRequestPermissionsResult requestCode = %d, grantResultsSize = %s", requestCode, grantResults.size)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun startLoadImage(imageURL: String) {
        Timber.d("startLoadImage %s", imageURL)
        imageLoader.load(this,
                imageURL,
                imageView!!,
                { presenter.onImageLoaded() },
                { presenter.onImageLoadFailed() })
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

    @AfterPermissionGranted(PERMISSION_REQUEST_SAVE)
    private fun saveImageWithCheckPermission() {
        if (hasStoragePermission()) {
            saveImage()
        } else {
            requestPermission(PERMISSION_REQUEST_SAVE)
        }
    }

    private fun requestPermission(permissionRequestCode: Int) {
        Timber.d("requestPermission")
        EasyPermissions.requestPermissions(this, getString(R.string.storage_rationale), permissionRequestCode, *STORAGE_PERMISSIONS)
    }

    private fun saveImage() {
        Timber.d("saveImage")
        val drawable = imageView!!.drawable as BitmapDrawable
        presenter.onSaveClick(position, drawable.bitmap)
    }

    @AfterPermissionGranted(PERMISSION_REQUEST_SHARE)
    private fun shareImageWithCheckPermission() {
        if (hasStoragePermission()) {
            shareImage()
        } else {
            requestPermission(PERMISSION_REQUEST_SHARE)
        }
    }

    private fun shareImage() {
        Timber.d("shareImage")
        val drawable = imageView!!.drawable as BitmapDrawable
        presenter.onShareClick(position, drawable.bitmap)
    }

    private fun hasStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(this, *STORAGE_PERMISSIONS)
    }

    private fun getParameters() {
        val bundle = intent.extras
        if (bundle != null) {
            position = bundle.getInt(PARAMETER_POSITION_TAG, 0)
        }
    }
}
