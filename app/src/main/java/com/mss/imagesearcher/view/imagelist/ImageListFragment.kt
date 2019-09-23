package com.mss.imagesearcher.view.imagelist


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.mss.imagesearcher.App

import com.mss.imagesearcher.R
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter
import com.mss.imagesearcher.view.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import javax.inject.Inject

class ImageListFragment : MvpAppCompatFragment(), ListFragmentView, ImageListAdapter.OnItemClickListener {

    companion object {
        const val PERMISSION_REQUEST_CODE = 777
        val NETWORK_PERMISSIONS = arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: ImageListPresenter

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerViewInit()
        srlImages!!.setOnRefreshListener { this.onRefresh() }
        checkNetworkPermissions()
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
        val intent = Intent(context, DetailActivity::class.java)
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


    private fun checkNetworkPermissions() {
        if (EasyPermissions.hasPermissions(context!!, *NETWORK_PERMISSIONS)) {
            Timber.d("Need NetworkPermissions")
            EasyPermissions.requestPermissions(this, getString(R.string.storage_rationale), PERMISSION_REQUEST_CODE, *NETWORK_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        Timber.d("onRequestPermissionsResult requestCode = %d, grantResultsSize = %s", requestCode, grantResults.size)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        if (!EasyPermissions.hasPermissions(context!!, *NETWORK_PERMISSIONS)) {
            Timber.d("Access deny Network")
            activity?.finish()
        }

    }

    override fun stopRefreshing() {
        srlImages.isRefreshing = false
    }

    override fun onItemClick(view: View, position: Int) {
        Timber.d("onItemClick")
        presenter.onItemClick(position)
    }


    private fun onRefresh() {
        Timber.d("onRefresh")
        //presenter.onRefresh(etSearch!!.text!!.toString())
    }

    private fun recyclerViewInit() {
        rvImages!!.itemAnimator = DefaultItemAnimator()
        rvImages!!.setHasFixedSize(true)
        rvImages!!.layoutManager = GridLayoutManager(context, 2)
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
}
