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
        model.loadHistoryFromDB()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ model.setHistoryInMemory(it) }, { Timber.e(it) })
    }

    fun onClearHistoryClick() {
        model.clearHistoryInDB()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ model.clearHistoryInMemory() }, { Timber.e(it) })
    }

    fun onItemClick(position: Int) {
        model.queryParamsList.value?.let {
            val queryParams = it.get(position)
            model.setCurrentQuery(queryParams)
            model.saveQueryParamsToDB(queryParams)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                model.popParamQueryToListInMemory(queryParams)
                                model.needShowPage.value = ImageListModel.PageType.LIST
                            },
                            { Timber.e(it) }
                    )
        }
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
