package com.mss.imagesearcher.presenter.history

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.R
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.model.entity.QueryParams
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
        model.queryParamsList.value?.let { list ->
            val queryParams = list[position]
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
            model.queryParamsList.value?.get(viewHolder.pos)?.let {
                viewHolder.setDescription(queryParamsToDescription(viewHolder, it))
            }
        }

        private fun queryParamsToDescription(viewHolder: IHistoryViewHolder, params: QueryParams): String {
            val sb = StringBuilder()
            params.imageType?.let { addStringToBuilder(sb, viewHolder.getStringByRes(R.string.image_type_label), it) }
            params.orientation?.let { addStringToBuilder(sb, viewHolder.getStringByRes(R.string.orientation_label), it) }
            params.category?.let { addStringToBuilder(sb, viewHolder.getStringByRes(R.string.category_label), it) }
            params.minWidth?.let { addStringToBuilder(sb, viewHolder.getStringByRes(R.string.min_width_label), it.toString()) }
            params.minHeight?.let { addStringToBuilder(sb, viewHolder.getStringByRes(R.string.min_height_label), it.toString()) }
            params.colors?.let { addStringToBuilder(sb, viewHolder.getStringByRes(R.string.colors_label), it) }
            params.imageOrder?.let { addStringToBuilder(sb, viewHolder.getStringByRes(R.string.order_label), it) }
            return sb.toString()
        }

        private fun addStringToBuilder(sb: StringBuilder, label: String, value: String) {
            if (!sb.isNullOrEmpty()) {
                sb.append(", ")
            }
            sb.append("$label = $value")
        }

        override fun getItemCount(): Int {
            return model.queryParamsList.value?.size ?: 0
        }

    }
}
