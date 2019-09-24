package com.mss.imagesearcher.presenter.imagelist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.view.imagelist.IImageListViewHolder
import com.mss.imagesearcher.view.imagelist.ListFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ImageListPresenter @Inject constructor(val model: ImageListModel) : MvpPresenter<ListFragmentView>() {

    val rvPresenter: RvPresenter

    private var lastQuery = ""
    private var nextPage = 1
    private var lastDisposableQuery: Disposable? = null
    private var end = false
    private var inProgress: Boolean? = false

    init {
        rvPresenter = RvPresenter()
    }

    fun onItemClick(position: Int) {
        Timber.d("onItemClick")
        viewState.openDetailActivity(position)
    }

    fun onNeedNextPage() {
        Timber.d("onNeedNextPage")
        if (end) {
            Timber.d("End results, return")
            return
        }
        synchronized(this) {
            if (inProgress!!) {
                Timber.d("in progress, return")
                return
            }
            inProgress = true
        }
        getNextPage()
    }


    private fun startSearch(searchText: String) {
        inProgress = true
        stopNetworkQuery()
        initNewQuery(searchText)
        getNextPage()
    }

    private fun initNewQuery(searchText: String) {
        Timber.d("initNewQuery")
        lastQuery = searchText
        nextPage = 1
        end = false
        model.clearImages()
        viewState.refreshImageList()
    }

    private fun stopNetworkQuery() {
        if (lastDisposableQuery != null) {
            Timber.d("stopNetworkQuery")
            lastDisposableQuery!!.dispose()
            lastDisposableQuery = null
        }
    }

    private fun getNextPage() {
        viewState.showProgress(true)
        loadImagesFromNetwork()
    }

    private fun loadImagesFromNetwork() {
        lastDisposableQuery = model.getImagesFromNetwork(lastQuery, nextPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ imagesNet ->
                    Timber.d("From network returns %d images on query '%s'", imagesNet.size, lastQuery)
                    doOnSuccess()
                },
                        { this.doOnError(it) })
    }

    private fun doOnError(throwable: Throwable) {
        Timber.d(throwable)
        inProgress = false
        viewState.showProgress(false)
        lastDisposableQuery = null
        end = true
        showMessageListEmpty()
    }

    private fun showMessageListEmpty() {
        if (model.images.isEmpty()) {
            Timber.d("showMessageListEmpty")
            viewState.showNotFoundMessage()
        }
    }

    private fun doOnSuccess() {
        Timber.d("doOnSuccess")
        inProgress = false
        nextPage += 1
        viewState.showProgress(false)
        viewState.refreshImageList()
        lastDisposableQuery = null
        showMessageListEmpty()
    }

    inner class RvPresenter : IRvImageListPresenter {

        override fun bindView(viewHolder: IImageListViewHolder) {
            viewHolder.showProgress(true)
            viewHolder.setImage(model.images[viewHolder.pos].webFormatURL!!)
        }

        override fun getItemCount(): Int {
            return model.images.size
        }

        override fun onImageLoaded(viewHolder: IImageListViewHolder) {
            viewHolder.showProgress(false)
        }

        override fun onImageLoadFailed(viewHolder: IImageListViewHolder) {
            viewHolder.showProgress(false)
        }
    }
}
