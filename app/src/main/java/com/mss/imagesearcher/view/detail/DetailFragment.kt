package com.mss.imagesearcher.view.detail

import android.Manifest
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
import kotlinx.android.synthetic.main.fragment_detail.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.hasPermissions
import timber.log.Timber
import javax.inject.Inject


class DetailFragment : MvpAppCompatFragment(), DetailView {

    companion object {
        private const val PERMISSION_REQUEST_SAVE = 555
        private const val PERMISSION_REQUEST_SHARE = 888
        private val STORAGE_PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    }

    @Inject
    @InjectPresenter
    lateinit var presenter: DetailPresenter

    @Inject
    lateinit var imageLoader: ImageLoader

    init {
        App.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    @ProvidePresenter
    fun providePresenter(): DetailPresenter {
        return presenter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
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
        presenter.onSaveClick(drawable.bitmap)
    }

    private fun shareImage() {
        Timber.d("shareImage")
        val drawable = imageView!!.drawable as BitmapDrawable
        presenter.onShareClick(drawable.bitmap)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Timber.d("onRequestPermissionsResult requestCode = %d, grantResultsSize = %s", requestCode, grantResults.size)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
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

    @AfterPermissionGranted(PERMISSION_REQUEST_SHARE)
    private fun shareImageWithCheckPermission() {
        if (hasStoragePermission()) {
            shareImage()
        } else {
            requestPermission(PERMISSION_REQUEST_SHARE)
        }
    }

    private fun hasStoragePermission(): Boolean {
        return hasPermissions(requireContext(), *STORAGE_PERMISSIONS)
    }

}
