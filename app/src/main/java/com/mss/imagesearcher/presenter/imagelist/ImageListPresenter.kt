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

    private var nextPage = 1
    private var lastDisposableQuery: Disposable? = null
    private var end = false
    private var inProgress: Boolean = false

    init {
        rvPresenter = RvPresenter()
        model.currentSearchString.observeForever { startSearch() }
    }

    fun onItemClick(position: Int) {
        Timber.d("onItemClick")
        model.currentImage.value = model.images[position]
    }

    fun onNeedNextPage() {
        Timber.d("onNeedNextPage")
        if (end) {
            Timber.d("End results, return")
            return
        }
        synchronized(inProgress) {
            if (!inProgress) {
                loadImagesFromNetwork()
            }
        }
    }

    fun onRefresh() {
        Timber.d("onRefresh")
        startSearch()
    }


    private fun startSearch() {
        stopNetworkQuery()
        initNewQuery()
        loadImagesFromNetwork()
    }

    private fun initNewQuery() {
        Timber.d("initNewQuery")
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

    private fun loadImagesFromNetwork() {
        model.currentSearchString.value?.let { searchString ->
            startProgress()
            lastDisposableQuery = model.getImagesFromNetwork(searchString, nextPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ imagesNet ->
                        Timber.d("From network returns %d images on query '%s'", imagesNet.size, searchString)
                        doOnSuccess()
                    },
                            { this.doOnError(it) })

        }
    }

    private fun doOnError(throwable: Throwable) {
        Timber.d(throwable)
        stopProgress()
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
        stopProgress()
        nextPage += 1
        viewState.refreshImageList()
        lastDisposableQuery = null
        showMessageListEmpty()
    }

    private fun startProgress() {
        synchronized(inProgress) {
            inProgress = true
        }
        viewState.showProgress(true)
    }

    private fun stopProgress() {
        synchronized(inProgress) {
            inProgress = false
        }
        viewState.showProgress(false)
    }

    inner class RvPresenter : IRvImageListPresenter {

        override fun bindView(viewHolder: IImageListViewHolder) {
            viewHolder.showProgress(true)
            viewHolder.setImage(model.images[viewHolder.pos].webFormatURL)
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
