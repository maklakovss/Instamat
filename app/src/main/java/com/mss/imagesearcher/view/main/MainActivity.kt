package com.mss.imagesearcher.view.main

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.mss.imagesearcher.App
import com.mss.imagesearcher.R
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter
import com.mss.imagesearcher.view.detail.DetailActivity
import com.mss.imagesearcher.view.imagelist.ImageListAdapter
import com.mss.imagesearcher.view.imagelist.ImageListView
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), ImageListView, ImageListAdapter.OnItemClickListener {

    companion object {
        const val PERMISSION_REQUEST_CODE = 777
        val NETWORK_PERMISSIONS = arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: ImageListPresenter

    @BindView(R.id.rvImages)
    var rvImages: RecyclerView? = null

    @BindView(R.id.etSearch)
    var etSearch: TextInputEditText? = null

    @BindView(R.id.pbList)
    var pbList: ProgressBar? = null

    @BindView(R.id.srlImages)
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    @BindView(R.id.adView)
    var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        recyclerViewInit()

        etSearch!!.setOnEditorActionListener { _, actionId, keyEvent -> this.onAction(actionId, keyEvent) }
        swipeRefreshLayout!!.setOnRefreshListener { this.onRefresh() }

        checkNetworkPermissions()

        presenter.onCreate()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.imagelist_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miPrivacyPolicy -> {
                presenter.onPrivacyPolicyClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        Timber.d("onRequestPermissionsResult requestCode = %d, grantResultsSize = %s", requestCode, grantResults.size)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        if (!EasyPermissions.hasPermissions(this, *NETWORK_PERMISSIONS)) {
            Timber.d("Access deny Network")
            finish()
        }

    }

    @ProvidePresenter
    fun providePresenter(): ImageListPresenter {
        return presenter
    }

    override fun refreshImageList() {
        Timber.d("refreshImageList")
        if (rvImages!!.adapter == null) {
            Timber.d("Create adapter")
            val adapter = ImageListAdapter(presenter.rvPresenter)
            adapter.setOnItemClickListener(this)
            rvImages!!.adapter = adapter
        } else {
            Timber.d("Adapter notifyDataSetChanged")
            rvImages!!.adapter!!.notifyDataSetChanged()
        }
    }

    override fun openDetailActivity(position: Int) {
        Timber.d("openDetailActivity")
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.PARAMETER_POSITION_TAG, position)
        startActivity(intent)
    }

    override fun showProgress(visible: Boolean) {
        Timber.d("showProgress %b", visible)
        if (visible) {
            pbList!!.visibility = View.VISIBLE
        } else {
            pbList!!.visibility = View.INVISIBLE
        }
    }

    override fun showNotFoundMessage() {
        Timber.d("showNotFoundMessage")
        Snackbar.make(rvImages!!, getString(R.string.not_found_message), Snackbar.LENGTH_SHORT).show()
    }

    override fun stopRefreshing() {
        swipeRefreshLayout!!.isRefreshing = false
    }

    override fun onItemClick(view: View, position: Int) {
        Timber.d("onItemClick")
        presenter.onItemClick(position)
    }

    private fun checkNetworkPermissions() {
        if (EasyPermissions.hasPermissions(this, *NETWORK_PERMISSIONS)) {
            Timber.d("Need NetworkPermissions")
            EasyPermissions.requestPermissions(this, getString(R.string.storage_rationale), PERMISSION_REQUEST_CODE, *NETWORK_PERMISSIONS)
        }
    }

    private fun onAction(actionId: Int, keyEvent: KeyEvent?): Boolean {
        var keyCode = 0
        if (keyEvent != null) {
            keyCode = keyEvent.keyCode
        }
        Timber.d("setOnEditorActionListener actionId = %d keyCode = %d", actionId, keyCode)
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            presenter.onSearchClick(etSearch!!.text!!.toString())
        }
        return false
    }

    private fun onRefresh() {
        Timber.d("onRefresh")
        presenter.onRefresh(etSearch!!.text!!.toString())
    }

    private fun recyclerViewInit() {
        rvImages!!.itemAnimator = DefaultItemAnimator()
        rvImages!!.setHasFixedSize(true)
        rvImages!!.layoutManager = GridLayoutManager(this, 2)
        rvImages!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val offsetOfEnd = recyclerView.computeVerticalScrollRange() - recyclerView.computeVerticalScrollOffset()

                    if (offsetOfEnd < 2 * recyclerView.height) {
                        presenter.onNeedNextPage()
                    }
                }
            }
        })
    }

    override fun initAdMob() {
        MobileAds.initialize(this, "ca-app-pub-8601890205077009~6067851844")
        val adRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build()
        adView!!.loadAd(adRequest)
    }

    override fun showPrivacyPolicy() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy_url)))
        startActivity(browserIntent)
    }

}
