package com.mss.imagesearcher.presenter.history

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.view.history.HistoryView
import com.mss.imagesearcher.view.history.IHistoryViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

@InjectViewState
class HistoryPresenter(private val model: ImageListModel) : MvpPresenter<HistoryView>() {

    val rvPresenter = RvPresenter()

    init {
        model.queryParamsList.observeForever { viewState.refreshHistoryList(it) }
        model.loadHistory().subscribe()
    }

    fun onClearHistoryClick() {
        model.clearHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { viewState.refreshHistoryList(model.queryParamsList.value) },
                        { Timber.e(it) }
                )
    }

    fun onItemClick(position: Int) {
        model.currentQuery.value = model.queryParamsList.value?.get(position)
        model.needShowPage.value = ImageListModel.PageType.LIST
    }

    inner class RvPresenter : IRvHistoryPresenter {

        override fun bindView(viewHolder: IHistoryViewHolder) {
            model.queryParamsList.value?.get(viewHolder.pos)?.query?.let { viewHolder.setText(it) }
            model.queryParamsList.value?.get(viewHolder.pos)?.toString()?.let { viewHolder.setDescription(it) }
        }

        override fun getItemCount(): Int {
            return model.queryParamsList.value?.size ?: 0
        }

    }
}
