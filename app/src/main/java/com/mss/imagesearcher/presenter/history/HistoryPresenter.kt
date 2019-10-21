package com.mss.imagesearcher.presenter.history

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mss.imagesearcher.R
import com.mss.imagesearcher.model.ImageListModel
import com.mss.imagesearcher.model.entity.QueryParams
import com.mss.imagesearcher.view.helpers.TranslateHelper
import com.mss.imagesearcher.view.history.HistoryView
import com.mss.imagesearcher.view.history.IHistoryViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

@InjectViewState
class HistoryPresenter(private val model: ImageListModel, private val translateHelper: TranslateHelper) : MvpPresenter<HistoryView>() {

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
            addStringToBuilderFromArray(sb, params.imageType, R.string.image_type_label, R.array.image_type)
            addStringToBuilderFromArray(sb, params.orientation, R.string.orientation_label, R.array.image_orientation)
            addStringToBuilderFromArray(sb, params.category, R.string.category_label, R.array.image_category)
            params.minWidth?.let { addStringToBuilder(sb, it.toString(), R.string.min_width_label) }
            params.minHeight?.let { addStringToBuilder(sb, it.toString(), R.string.min_height_label) }
            addStringToBuilderFromArray(sb, params.colors, R.string.colors_label, R.array.image_colors)
            addStringToBuilderFromArray(sb, params.imageOrder, R.string.order_label, R.array.image_order)
            return sb.toString()
        }

        private fun addStringToBuilderFromArray(sb: StringBuilder, value: String?, idLabel: Int, idArray: Int) {
            if (value != null && value != "all") {
                addStringToBuilder(sb, translateHelper.getTranslatedValueFromArray(idArray, value), idLabel)
            }
        }

        private fun addStringToBuilder(sb: StringBuilder, value: String, idLabel: Int) {
            if (value.isNotEmpty()) {
                if (!sb.isEmpty()) {
                    sb.append(", ")
                }
                val label = translateHelper.getTranslatedStringByRes(idLabel)
                sb.append("$label = $value")
            }
        }

        override fun getItemCount(): Int {
            return model.queryParamsList.value?.size ?: 0
        }

    }
}
